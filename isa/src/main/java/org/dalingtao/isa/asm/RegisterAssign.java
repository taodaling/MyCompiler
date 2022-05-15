package org.dalingtao.isa.asm;

import org.dalingtao.DSU;
import org.dalingtao.isa.abstractasm.Copy;
import org.dalingtao.isa.abstractasm.Ins;
import org.dalingtao.isa.abstractasm.LoadStack;
import org.dalingtao.isa.abstractasm.Registers;
import org.dalingtao.isa.abstractasm.StoreStack;
import org.dalingtao.isa.dataflow.LiveVarAnalysis;
import org.dalingtao.sa.st.FunctionDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegisterAssign {
    Var register(String x) {
        Var var = indexer.get(x);
        if (var == null) {
            var = new Var();
            var.id = vars.size();
            var.name = x;
            indexer.put(x, var);
            vars.add(var);
        }
        return var;
    }

    Map<String, Var> indexer = new HashMap<>();
    List<Var> vars = new ArrayList<>();
    int stackOffset;


    public Result solve(List<Ins> instructs, FunctionDef def) {
        stackOffset = 0;
        return solve0(instructs, def);
    }

    private Set<Var> merge(Set<Var> a, Set<Var> b) {
        Set<Var> res = new LinkedHashSet<>(a);
        for (var x : b) {
            res.add(x);
        }
        return res;
    }

    PriorityQueue<Event> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.deg));

    void remove(Var var, Var item) {
        var.deg -= var.adj.remove(item) ? 1 : 0;
    }

    void add(Var var, Var item) {
        var.deg += var.adj.add(item) ? 1 : 0;
    }

    //start
    private Result solve0(List<Ins> instructs, FunctionDef def) {
        //assign code
        LiveVarAnalysis analysis = new LiveVarAnalysis(instructs);
        var interfere = analysis.calcLiveSet();
        indexer = new HashMap<>();
        vars = new ArrayList<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.deg));
        for (var ins : instructs) {
            Stream.of(ins.getDef(), ins.getUse())
                    .flatMap(x -> Arrays.stream(x))
                    .forEach(this::register);
        }
        int K = Registers.allUsableRegisters.length;
        for (String s : Registers.allUsableRegisters) {
            register(s);
        }
        for (String s : Registers.allUsableRegisters) {
            for (String t : Registers.allUsableRegisters) {
                if (s == t) {
                    continue;
                }
                indexer.get(s).adj.add(indexer.get(t));
            }
        }
        for (var set : interfere) {
            for (var a : set) {
                for (var b : set) {
                    if (a == b) {
                        continue;
                    }
                    indexer.get(a).adj.add(indexer.get(b));
                }
            }
        }

        for (Var var : vars) {
            var.deg = var.adj.size();
        }
        for (var item : Registers.allUsableRegisters) {
            indexer.get(item).deg = (int) 2e9;
        }


        int m = vars.size();
        for (Ins ins : instructs) {
            if (ins instanceof Copy) {
                Var from = indexer.get(ins.getUse()[0]);
                Var to = indexer.get(ins.getDef()[0]);
                from.moveRelated = true;
                to.moveRelated = true;
            }
        }


        boolean mergeHappen = false;
        DSU dsu = new DSU(m);
        for (var item : Registers.allUsableRegisters) {
            dsu.size()[indexer.get(item).id] = vars.size() + 1;
        }

        List<Var> deleted = new ArrayList<>();
        while (true) {
            for (Var var : vars) {
                if (!var.moveRelated && !var.del) {
                    pq.add(new Event(var.deg, var));
                }
            }
            while (!pq.isEmpty() && pq.peek().deg < K) {
                Var head = pq.remove().var;
                if (head.del || head.moveRelated) {
                    continue;
                }
                head.del = true;
                deleted.add(head);
                for (Var adjVar : head.adj) {
                    remove(adjVar, head);
                    pq.add(new Event(adjVar.deg, adjVar));
                }
            }
            //merge variable
            boolean happen = false;
            for (Ins ins : instructs) {
                if (ins instanceof Copy) {
                    Var from = indexer.get(ins.getUse()[0]);
                    Var to = indexer.get(ins.getDef()[0]);
                    if (!from.moveRelated || !to.moveRelated) {
                        continue;
                    }
                    from = vars.get(dsu.find(from.id));
                    to = vars.get(dsu.find(to.id));
                    if (from == to) {
                        continue;
                    }
                    if (from.adj.contains(to)) {
                        continue;
                    }
                    //ok
                    Set<Var> mergeAdj = merge(from.adj, to.adj);
                    if (mergeAdj.size() >= K && from.name.startsWith("T") && to.name.startsWith("T")) {
                        continue;
                    }
                    dsu.union(from.id, to.id);
                    if (dsu.find(from.id) != from.id) {
                        Var tmp = from;
                        from = to;
                        to = tmp;
                    }
                    from.deg -= from.adj.size();
                    from.deg += mergeAdj.size();
                    from.adj = mergeAdj;
                    for (var item : from.adj) {
                        remove(item, to);
                        add(item, from);
                    }
                    System.out.println("Merge " + from + ", " + to);
                    happen = true;
                    mergeHappen = true;
                }
            }

            //freeze
            for (Var var : vars) {
                if (var.moveRelated && var.deg < K) {
                    var.moveRelated = false;
                    happen = true;
                }
            }

            if (!happen) {
                break;
            }
        }

        //repaint instruct if necessary
        if (mergeHappen) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < m; i++) {
                map.put(vars.get(i).name, vars.get(dsu.find(i)).name);
            }
            for (Ins ins : instructs) {
                ins.repaint(map);
            }
        }
        //remove unused copy instructs
        instructs = instructs.stream().filter(x -> !(x instanceof Copy && x.getDef()[0].equals(x.getUse()[0])))
                .collect(Collectors.toList());
        for (Var var : vars) {
            if (!var.del) {
                pq.add(new Event(var.deg, var));
            }
        }
        while (!pq.isEmpty()) {
            Var head = pq.remove().var;
            if (head.del) {
                continue;
            }
            head.del = true;
            deleted.add(head);
            if (head.deg >= K) {
                head.spill = true;
            }
            for (Var adjVar : head.adj) {
                remove(adjVar, head);
                pq.add(new Event(adjVar.deg, adjVar));
            }
        }

        Collections.reverse(deleted);
        boolean spill = false;
        for (Var var : deleted) {
            if(dsu.find(var.id) != var.id) {
                continue;
            }
            boolean[] used = new boolean[K];
            for (Var neighbor : var.adj) {
                if (neighbor.color != -1) {
                    used[neighbor.color] = true;
                }
            }
            int choice = 0;
            while (choice < K && used[choice]) {
                choice++;
            }
            var.color = choice < K ? choice : -1;
            var.spill = var.color == -1;
            if (var.spill) {
                spill = true;
                var.offset = stackOffset;
                stackOffset += 8;
            }
        }

        if (spill) {
            List<Ins> newIns = new ArrayList<>();
            //again
            for (Ins ins : instructs) {
                //insert load ans store
                for (String s : ins.getUse()) {
                    Var var = indexer.get(s);
                    if (var.spill) {
                        newIns.add(new LoadStack("" + var.offset,
                                var.name));
                    }
                }
                newIns.add(ins);
                for (String s : ins.getDef()) {
                    Var var = indexer.get(s);
                    if (var.spill) {
                        newIns.add(new StoreStack("" + var.offset,
                                var.name));
                    }
                }
            }
            return solve0(newIns, def);
        } else {
            String[] colorName = new String[K];
            for (int i = 0; i < K; i++) {
                colorName[indexer.get(Registers.allUsableRegisters[i]).color] = Registers.allUsableRegisters[i];
            }
            Map<String, String> map = new HashMap<>();
            for (Var var : vars) {
                if(dsu.find(var.id) != var.id) {
                    continue;
                }
                map.put(var.name, colorName[var.color]);
            }
            for (Ins ins : instructs) {
                ins.repaint(map);
            }
            instructs = instructs.stream().filter(x -> !(x instanceof Copy && x.getDef()[0].equals(x.getUse()[0])))
                    .collect(Collectors.toList());
            return new Result(instructs, map, stackOffset);
        }
    }

    static class Event {
        int deg;
        Var var;

        public Event(int deg, Var var) {
            this.deg = deg;
            this.var = var;
        }
    }

    static class Var {
        int id;
        String name;
        int color = -1;
        int deg;
        boolean spill;
        Set<Var> adj = new HashSet<>();
        boolean del;
        int offset = -1;
        boolean moveRelated;

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Result {
        Map<String, String> color;
        List<Ins> ins;
        int stack;

        public Result(List<Ins> instructs, Map<String, String> map, int stack) {
            this.ins = instructs;
            this.color = map;
            this.stack = (stack + 16 - 1) / 16 * 16;
        }

        public Map<String, String> getColor() {
            return color;
        }

        public List<Ins> getIns() {
            return ins;
        }

        public int getStack() {
            return stack;
        }
    }
}
