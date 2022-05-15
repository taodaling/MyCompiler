package org.dalingtao.isa.tile;

import org.dalingtao.Context;
import org.dalingtao.isa.abstractasm.Copy;
import org.dalingtao.isa.abstractasm.Ins;
import org.dalingtao.isa.abstractasm.Registers;
import org.dalingtao.isa.abstractasm.Ret;
import org.dalingtao.sa.ir.Call;
import org.dalingtao.sa.ir.Instruction;
import org.dalingtao.sa.ir.Label;
import org.dalingtao.sa.ir.Return;
import org.dalingtao.sa.ir.Temp;
import org.dalingtao.sa.st.FunctionDef;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TileChooser {
    List<Tile> tiles;
    Temp[] tempsForRestoreNonVolatileRegisters ;

    public void setTempsForRestoreNonVolatileRegisters(Temp[] tempsForRestoreNonVolatileRegisters) {
        this.tempsForRestoreNonVolatileRegisters = tempsForRestoreNonVolatileRegisters;
    }

    public TileChooser(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void choose(Instruction instruction, Consumer<Ins> ps) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (instruction instanceof Label) {
            Label label = (Label) instruction;
            String name = label.getName();
            ps.accept(new org.dalingtao.isa.abstractasm.Label(name));

            return;
        }

        //return
        if (instruction instanceof Return.ReturnVal) {
            //insert pop command
            //function label
            //insert save instruction
            Temp[] alloc = tempsForRestoreNonVolatileRegisters;
            for (int i = 0; i < Registers.nonVolatileRegisters.length && i < alloc.length; i++) {
                ps.accept(new Copy("mov", Registers.nonVolatileRegisters[i], alloc[i].getId()));
            }
            ps.accept(new Copy("mov", Registers.rax, ((Temp) ((Return.ReturnVal) instruction).getE()).getId()));
            ps.accept(new Ret());
            return;
        }
        cache = new HashMap<>();
        choose(instruction);
        emit(instruction, ps);
    }

    Map<Object, TileChoice> cache;

    private String emit(Instruction ins, Consumer<Ins> consumer) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //call
        if (ins instanceof Call) {
            //save volatile
            Temp[] save = new Temp[Registers.volatileRegisters.length];
            for (int i = 0; i < save.length; i++) {
                save[i] = new Temp(Context.getInstance().nextId());
            }

            for (int i = 0; i < save.length; i++) {
                consumer.accept(new Copy("mov", save[i].getId(), Registers.volatileRegisters[i]));
            }

            //move args
            Call call = (Call) ins;
            var temps = call.getA();
            for (int i = 0; i < Registers.arguments.length && i < temps.length; i++) {
                consumer.accept(new Copy("mov", Registers.arguments[i], ((Temp) temps[i]).getId()));
            }
            consumer.accept(new org.dalingtao.isa.abstractasm.Call("call", call.getF().getLabel()));
            Temp retVal = new Temp(Context.getInstance().nextId());
            consumer.accept(new Copy("mov", retVal.getId(), Registers.rax));


            for (int i = 0; i < save.length; i++) {
                consumer.accept(new Copy("mov", Registers.volatileRegisters[i], save[i].getId()));
            }

            return retVal.getId();
        }
        TileChoice choice = cache.get(ins);
        Map<String, String> param = new HashMap<>();
        for (var entry : choice.items.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (v instanceof Instruction) {
                v = emit((Instruction) v, consumer);
            }
            param.put(k, (String) v);
        }
        if (param.containsKey("$")) {
            if (param.size() != 1) {
                throw new TileException("If $ parameter show up, only one parameter is allowed");
            }
            return param.get("$");
        }
        //as dest
        param.put("$", new Temp(Context.getInstance().nextId()).getId());
        for (var x : choice.tile.instructs) {
            consumer.accept(x.get(param));
        }
        return param.get("$");
    }

    private TileChoice choose(Instruction ins) {
        if (ins instanceof Call) {
            return new TileChoice();
        }
        if (!cache.containsKey(ins)) {
            TileChoice choice = null;
            for (Tile tile : tiles) {
                Map<String, Object> extract = new HashMap<>();
                if (tile.root.match(ins, extract)) {
                    TileChoice another = new TileChoice();
                    another.cost = tile.cost;
                    for (Object v : extract.values()) {
                        if (v instanceof Instruction) {
                            another.cost += choose((Instruction) v).cost;
                        }
                    }
                    another.items = extract;
                    another.tile = tile;
                    if (choice == null || choice.cost > another.cost) {
                        choice = another;
                    }
                }
            }
            if (choice == null) {
                throw new TileException("No compatible tile found for: " + ins);
            }
            cache.put(ins, choice);
        }
        return cache.get(ins);
    }
}