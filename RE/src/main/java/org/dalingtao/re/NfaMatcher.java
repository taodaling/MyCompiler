package org.dalingtao.re;

import java.util.BitSet;

public class NfaMatcher implements Matcher {
    State start;
    BitSet cur;
    BitSet next;
    State[] states;

    NfaMatcher(State start, State[] states) {
        this.start = start;
        this.states = states;
        next = new BitSet(states.length);
        cur = new BitSet(states.length);
        dfs(start.getId());
        swapBuf();
    }

    void dfs(int root) {
        if (next.get(root)) {
            return;
        }
        next.set(root);
        for (Transfer t : states[root].adj()) {
            dfs(t.to().getId());
        }
    }

    void swapBuf() {
        BitSet tmp = cur;
        cur = next;
        next = tmp;
    }

    public void consume(int c) {
        next.clear();
        for (int i = 0; i < states.length; i++) {
            if (!cur.get(i)) {
                continue;
            }
            Transfer t = states[i].get(c);
            if (t == null) {
                continue;
            }
            State state = t.to();
            dfs(state.getId());
        }
        swapBuf();
    }

    public boolean match() {
        boolean ok = false;
        for (int i = 0; i < states.length; i++) {
            if (cur.get(i)) {
                ok |= states[i] instanceof FinalState;
            }
        }
        return ok;
    }
}
