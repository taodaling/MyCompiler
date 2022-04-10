package org.dalingtao.re;

import java.util.Set;

public class NotMatchMultiState extends BaseState {
    Set<Integer> set;
    Transfer to;


    @Override
    public Transfer get(int c) {
        return !set.contains(c) ? to : null;
    }
}
