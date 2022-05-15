package org.dalingtao.sa.cfg;

import org.dalingtao.sa.ir.Cjump;
import org.dalingtao.sa.ir.Jump;
import org.dalingtao.sa.ir.Label;
import org.dalingtao.sa.ir.Statement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Trace {
    static class Event {
        BasicBlock block;
        int deg;

        public Event(BasicBlock block, int deg) {
            this.block = block;
            this.deg = deg;
        }
    }

    PriorityQueue<Event> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.deg));
    List<List<BasicBlock>> traces = new ArrayList<>();
    List<BasicBlock> building = new ArrayList<>();

    void process(BasicBlock b) {
        pq.add(new Event(b, b.indeg));
    }

    void remove(BasicBlock b) {
        b.color = true;
        building.add(b);
        for (BasicBlock node : b.adj) {
            node.indeg--;
            process(node);
        }
    }

    public List<Statement> reorder(List<BasicBlock> blocks) {
        Map<String, BasicBlock> labelToBlock = new HashMap<>();
        for (BasicBlock block : blocks) {
            Statement first = block.s.get(0);
            if (first instanceof Label) {
                Label label = (Label) first;
                labelToBlock.put(label.getName(), block);
            }
        }
        for (BasicBlock block : blocks) {
            Statement last = block.s.get(block.s.size() - 1);
            if (last instanceof Jump) {
                Jump jump = (Jump) last;
                block.adj.add(labelToBlock.get(jump.getLabel().getLabel()));
            } else if (last instanceof Cjump) {
                Cjump cjump = (Cjump) last;
                block.adj.add(labelToBlock.get(cjump.getT().getLabel()));
                block.adj.add(labelToBlock.get(cjump.getF().getLabel()));
            }
            for (BasicBlock node : block.adj) {
                node.indeg++;
            }
        }

        for (BasicBlock block : blocks) {
            pq.add(new Event(block, block.indeg));
        }
        while (!pq.isEmpty()) {
            Event e = pq.remove();
            if (e.block.color) {
                continue;
            }
            BasicBlock last = e.block;
            building = new ArrayList<>();
            remove(last);
            while (true) {
                BasicBlock best = null;
                for (BasicBlock block : last.adj) {
                    if (block.color) {
                        continue;
                    }
                    best = block;
                    break;
                }
                if (best == null) {
                    break;
                }
                last = best;
                remove(last);
            }
            traces.add(building);
        }

        List<Statement> all = new ArrayList<>();
        //remove
        for (List<BasicBlock> trace : traces) {
            List<Statement> join = new ArrayList<>();
            for (BasicBlock b : trace) {
                join.addAll(b.s);
            }
            List<Statement> collect = new ArrayList<>();
            int n = join.size();
            //remove non sense operation
            for (int i = 0; i < n; i++) {
                Statement s = join.get(i);
                if (s instanceof Jump) {
                    Jump jump = (Jump) s;
                    if (i + 1 < n) {
                        var next = join.get(i + 1);
                        if (next instanceof Label && ((Label) next).getName().equals(jump.getLabel().getLabel())) {
                            continue;
                        }
                    }
                } else if (s instanceof Cjump) {
                    Cjump jump = (Cjump) s;
                    if (i + 1 < n) {
                        var next = join.get(i + 1);
                        if (next instanceof Label && ((Label) next).getName().equals(jump.getT().getLabel())) {
                            collect.add(jump.negate());
                            continue;
                        }
                    } else {
                        //insert false
                        collect.add(jump);
                        collect.add(new Jump(jump.getF()));
                        continue;
                    }
                }
                collect.add(s);
            }
            all.addAll(collect);
        }

        return all;
    }
}
