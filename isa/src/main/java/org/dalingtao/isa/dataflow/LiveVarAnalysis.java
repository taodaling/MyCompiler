package org.dalingtao.isa.dataflow;

import org.dalingtao.isa.abstractasm.Cjmp;
import org.dalingtao.isa.abstractasm.Copy;
import org.dalingtao.isa.abstractasm.FLabel;
import org.dalingtao.isa.abstractasm.Ins;
import org.dalingtao.isa.abstractasm.Jmp;
import org.dalingtao.isa.abstractasm.Label;
import org.dalingtao.isa.abstractasm.Load;
import org.dalingtao.isa.abstractasm.Ret;
import org.dalingtao.isa.abstractasm.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LiveVarAnalysis {
    List<Node> nodes;


    void addEdge(Node a, Node b) {
        a.adjOut.add(b);
        b.adjIn.add(a);
    }

    public LiveVarAnalysis(List<Ins> instructs) {
        nodes = instructs.stream().map(x -> {
            Node node = new Node();
            node.ins = x;
            node.type = type(x);
            return node;
        }).collect(Collectors.toList());

        Map<String, Node> map = new HashMap<>();
        for (Node node : nodes) {
            if (node.ins instanceof Label) {
                String label = ((Label) node.ins).getLabel();
                map.put(label, node);
            }
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.ins instanceof Jmp) {
                String target = ((Jmp) node.ins).getTarget();
                addEdge(node, map.get(target));
                continue;
            } else if (node.ins instanceof Cjmp) {
                String target = ((Jmp) node.ins).getTarget();
                addEdge(node, map.get(target));
            }
            if (i + 1 < nodes.size()) {
                addEdge(node, nodes.get(i + 1));
            }
        }

        for (Node node : nodes) {
            for (String name : node.ins.getUse()) {
                if (node.in.contains(name)) {
                    continue;
                }
                node.in.add(name);
                afterUpdatingInChange(node, name);
            }
        }
    }


    public List<List<String>> calcLiveSet() {
        List<List<String>> ans = new ArrayList<>();
        for (Node root : nodes) {
            for (Node node : root.adjOut) {
                List<String> intersect = new ArrayList<>();
                for (String x : root.out) {
                    if (node.in.contains(x)) {
                        intersect.add(x);
                    }
                }
                ans.add(intersect);
            }
        }
        return ans;
    }

    int type(Ins ins) {
        if (ins instanceof Copy || ins instanceof Load) {
            return 1;
        }
        if (ins instanceof Store) {
            return 2;
        }
        if (ins instanceof Cjmp) {
            return 3;
        }
        if (ins instanceof FLabel) {
            return 4;
        }
        if (ins instanceof Ret) {
            return 5;
        }
        return 0;
    }

    // in
    // out
    public void propagateOutChange(Node root, Node neighbor, String item) {
        if (root.in.contains(item) || Arrays.asList(root.ins.getDef()).contains(item)) {
            return;
        }
        root.in.add(item);
        afterUpdatingInChange(root, item);
    }

    public void afterUpdatingInChange(Node root, String item) {
        for (Node node : root.adjIn) {
            propagateInChange(node, root, item);
        }
    }

    public void propagateInChange(Node root, Node neighbor, String item) {
        if (root.out.contains(item)) {
            return;
        }
        root.out.add(item);
        afterUpdatingOutChange(root, item);
    }

    public void afterUpdatingOutChange(Node root, String item) {
        propagateOutChange(root, root, item);
    }

    static class Node {
        List<Node> adjIn = new ArrayList<>();
        List<Node> adjOut = new ArrayList<>();
        int type;
        Ins ins;
        Set<String> in = new HashSet<>();
        Set<String> out = new HashSet<>();

        @Override
        public String toString() {
            return ins.toASM();
        }
    }

}
