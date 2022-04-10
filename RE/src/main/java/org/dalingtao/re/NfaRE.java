package org.dalingtao.re;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NfaRE implements REEngine {
    State start;
    List<State> accept;
    State[] all;

    NfaRE() {
    }

    public void addPropertyForFinalState(String name, Object value) {
        accept.forEach(x -> x.getProperties().put(name, value));
    }

    public NfaMatcher matcher() {
        return new NfaMatcher(start, all);
    }

    public DfaRE toDfa(int charset) {
        return new DfaRE(this, charset);
    }

    public static NfaRE merge(NfaRE... res) {
        List<State> all = new ArrayList<>();
        List<State> accept = new ArrayList<>();

        State first = new BaseState();
        all.add(first);

        for (NfaRE re : res) {
            all.addAll(Arrays.asList(re.all));
            accept.addAll(re.accept);
            first.adj().add(new TransferImpl(re.start));
        }

        NfaRE ans = new NfaRE();
        for (int i = 0; i < all.size(); i++) {
            ((BaseState) all.get(i)).id = i;
        }

        ans.accept = accept;
        ans.all = all.toArray(new State[0]);
        ans.start = first;
        return ans;
    }

}
