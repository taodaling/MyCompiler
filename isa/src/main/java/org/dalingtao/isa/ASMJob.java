package org.dalingtao.isa;

import org.dalingtao.Context;
import org.dalingtao.ToStringXml;
import org.dalingtao.isa.abstractasm.Copy;
import org.dalingtao.isa.abstractasm.Ins;
import org.dalingtao.isa.abstractasm.Registers;
import org.dalingtao.isa.asm.GenASM;
import org.dalingtao.isa.asm.RegisterAssign;
import org.dalingtao.isa.tile.ISACost;
import org.dalingtao.isa.tile.TileCompiler;
import org.dalingtao.sa.ir.Temp;
import org.dalingtao.sa.sample.ast.RunSemanticAnalysis;
import org.dalingtao.sa.st.FunctionDef;
import org.dalingtao.sa.st.FunctionModule;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ASMJob {
    public static String loadMiniJava() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        var statments = RunSemanticAnalysis.loadMiniJava();
        ISACost cost = new ISACost(ASMJob.class
                .getClassLoader().getResourceAsStream("isa.cost"));
        var tileChooser = new TileCompiler().compile(cost, ASMJob.class
                .getClassLoader().getResourceAsStream("isa.tile"));

        Context.getInstance().setScopeMap(statments.stream()
                .map(x -> x.getF()).collect(Collectors.toMap(x -> x.getName(),
                        x -> x)));
        List<FunctionModule<List<Ins>>> abstractAsms = statments.stream()
                .map(x -> {
                    Context.getInstance().setScope(x);
                    List<Ins> item = new ArrayList<>();
                    FunctionDef f = x.getF();
                    //function label
                    //insert save instruction
                    Temp[] temps = new Temp[Registers.nonVolatileRegisters.length];
                    for (int i = 0; i < temps.length; i++) {
                        temps[i] = new Temp(Context.getInstance().nextId());
                        item.add(new Copy("mov", temps[i].getId(), Registers.nonVolatileRegisters[i]));
                    }
                    //move arg
                    for (int i = 0; i < f.getArgTypes().size(); i++) {
                        item.add(new Copy("mov", new Temp(f.getArgTypes().get(i).getSeqId()).getId(), Registers.arguments[i]));
                    }
                    tileChooser.setTempsForRestoreNonVolatileRegisters(temps);
                    for (var s : x.getItem()) {
                        try {
                            tileChooser.choose(s, item::add);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return new FunctionModule<>(x.getF(), item);
                }).collect(Collectors.toList());
        System.out.println(ToStringXml.toString("ASM", abstractAsms));

        String asm = abstractAsms.stream()
                .map(x -> {
                    var res = new RegisterAssign().solve(x.getItem(), x.getF());
                    var string = new GenASM().gen(res.getIns(), x.getF(), res.getStack());
                    System.out.println(string);
                    return string;
                }).collect(Collectors.joining("\n"));

        return asm;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var res = loadMiniJava();
        System.out.println(res);
    }
}
