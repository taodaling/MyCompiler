package org.dalingtao.isa.abstractasm;

public class FLabel extends Label {
    public FLabel(String... data) {
        super(data);
    }

    public int localSize;
}
