package org.dalingtao.re;

import java.util.Set;

public class MatchMultiState extends BaseState {
    Set<Integer> set;
    Transfer to;

    public MatchMultiState() {
    }

    @Override
    public Transfer get(int c) {
        return set.contains(c) ? to : null;
    }
}
