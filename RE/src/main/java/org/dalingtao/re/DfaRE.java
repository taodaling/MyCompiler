package org.dalingtao.re;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DfaRE implements REEngine {
    List<int[]> adj = new ArrayList<>();
    Map<BitSet, Integer> mapping = new HashMap<>();
    List<BitSet> sets = new ArrayList<>();
    Deque<Integer> pending = new LinkedList<>();
    boolean[] accept;
    int charset;
    State[] states;

    int getId(BitSet bitSet) {
        Integer ans = mapping.get(bitSet);
        if (ans == null) {
            ans = mapping.size();
            mapping.put(bitSet, ans);
            pending.addLast(ans);
            sets.add(bitSet);
            adj.add(new int[charset]);
        }
        return ans;
    }

    public DfaRE(NfaRE re, int charset) {
        int n = re.all.length;
        this.charset = charset;
        this.states = re.all;
        getId(new BitSet(n));
        getId(new Dfs(re.all).add(re.start.getId()).set);

        while (!pending.isEmpty()) {
            int head = pending.removeFirst();
            BitSet bs = sets.get(head);
            for (int j = 0; j < charset; j++) {
                Dfs dfs = new Dfs(re.all);
                for (int i = 0; i < n; i++) {
                    if (bs.get(i)) {
                        Transfer t = re.all[i].get(j);
                        if (t != null) {
                            dfs.add(t.to().getId());
                        }
                    }
                }
                adj.get(head)[j] = getId(dfs.set);
            }
        }

        int m = mapping.size();
        accept = new boolean[m];
        for (int i = 0; i < m; i++) {
            for (State a : re.accept) {
                if (sets.get(i).get(a.getId())) {
                    accept[i] = true;
                }
            }
        }
    }

    @Override
    public Matcher matcher() {
        return new DfaMatcher(this);
    }

    static class Dfs {
        BitSet set;
        State[] all;

        public Dfs(State[] all) {
            set = new BitSet(all.length);
            this.all = all;
        }

        Dfs add(int x) {
            if (set.get(x)) {
                return this;
            }
            set.set(x);
            for (Transfer t : all[x].adj()) {
                add(t.to().getId());
            }
            return this;
        }
    }
}
