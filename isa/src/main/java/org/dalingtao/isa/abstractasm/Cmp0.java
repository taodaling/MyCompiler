package org.dalingtao.isa.abstractasm;

public class Cmp0 extends Ins {
    public Cmp0(String[] data) {
        super(data);
        setUse(data[1]);
    }
}
