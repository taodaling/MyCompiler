package org.dalingtao.re;

public class MatchAllState extends BaseState{
    Transfer to;

    @Override
    public Transfer get(int c) {
        return to;
    }
}
