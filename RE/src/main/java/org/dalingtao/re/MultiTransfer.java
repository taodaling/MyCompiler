package org.dalingtao.re;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MultiTransfer implements Transfer {
    Collection<Transfer> transfers;

    public MultiTransfer(Transfer... transfers) {
        this.transfers = Arrays.asList(transfers);
    }

    @Override
    public State to() {
        return transfers.stream().findAny().get().to();
    }

    @Override
    public void set(State state) {
        transfers.stream().forEach(x -> x.set(state));
    }
}
