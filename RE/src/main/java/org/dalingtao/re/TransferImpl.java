package org.dalingtao.re;

public class TransferImpl implements Transfer {
    State to;

    public TransferImpl(State to) {
        this.to = to;
    }

    @Override
    public State to() {
        return to;
    }

    @Override
    public void set(State state) {
        assert to == null;
        to = state;
    }

    @Override
    public String toString() {
        return "" + to;
    }
}
