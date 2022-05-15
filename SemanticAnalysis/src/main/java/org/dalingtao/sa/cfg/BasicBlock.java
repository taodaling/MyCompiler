package org.dalingtao.sa.cfg;

import org.dalingtao.sa.ir.CanonicalStatement;
import org.dalingtao.sa.ir.Cjump;
import org.dalingtao.sa.ir.Jump;
import org.dalingtao.sa.ir.Label;
import org.dalingtao.sa.ir.Return;
import org.dalingtao.sa.ir.Statement;
import org.dalingtao.ToStringXml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicBlock {
    List<Statement> s;
    List<BasicBlock> adj = new ArrayList<>();
    boolean color;
    int indeg;

    public BasicBlock(List<Statement> s) {
        this.s = s;
    }

    private static int typeOf(Statement b) {
        //0 - normal
        //1 - jump
        //2 - label
        if (b instanceof Return || b instanceof Jump || b instanceof Cjump) {
            return 1;
        }
        if (b instanceof Label) {
            return 2;
        }
        return 0;
    }

    public static List<BasicBlock> split(CanonicalStatement cs) {
        List<Statement> ss = cs.toList();
        List<BasicBlock> basicBlocks = new ArrayList<>();
        List<Statement> building = null;
        for (Statement s : ss) {
            int t = typeOf(s);
            if (building == null) {
                if (t == 1) {
                    basicBlocks.add(new BasicBlock(Arrays.asList(s)));
                } else {
                    building = new ArrayList<>();
                    building.add(s);
                }
            } else {
                if (t == 0) {
                    building.add(s);
                } else if (t == 1) {
                    building.add(s);
                    basicBlocks.add(new BasicBlock(building));
                    building = null;
                } else {
                    basicBlocks.add(new BasicBlock(building));
                    building = new ArrayList<>();
                    building.add(s);
                }
            }
        }
        if (building != null) {
            basicBlocks.add(new BasicBlock(building));
        }
        for (int i = 0; i < basicBlocks.size(); i++) {
            BasicBlock block = basicBlocks.get(i);
            Statement lastStatement = block.s.get(block.s.size() - 1);
            if (typeOf(lastStatement) != 1 && i + 1 < basicBlocks.size()) {
                Statement next = basicBlocks.get(i + 1).s.get(0);
                block.s.add(new Jump(((Label) next).toName()));
            }
        }
        return basicBlocks;
    }

    @Override
    public String toString() {
        return ToStringXml.toString("BasicBlock", s);
    }
}
