package org.dalingtao.isa.abstractasm;

public class Cmp extends Ins {
    public Cmp(String... data) {
        super(data);
        setUse(data[1], data[2]);
    }
}
