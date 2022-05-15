package org.dalingtao.isa.tile;

import org.dalingtao.sa.ir.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TileNode {
    String alias;
    Class type;
    List<TileNode> adj = new ArrayList<>();

    public boolean match(Object item, Map<String, Object> context) {
        if (!type.isAssignableFrom(item.getClass())) {
            return false;
        }
        if (adj.isEmpty()) {
            context.put(alias, item);
            return true;
        }
        if (!(item instanceof Instruction)) {
            return false;
        }
        Instruction instruction = (Instruction) item;
        Object[] args = instruction.getArgs();
        if (adj.size() != args.length) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (!adj.get(i).match(args[i], context)) {
                return false;
            }
        }
        return true;
    }
}
