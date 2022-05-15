package org.dalingtao.isa.abstractasm;

public class Bop extends Ins {
    public Bop(String... data) {
        super(data);
        setDef(data[1]);
        setUse(data[1], data[2]);
    }
}
