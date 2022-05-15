package org.dalingtao.isa.abstractasm;

public class Set extends Ins {
    public Set(String... data) {
        super(data);
        setDef(data[1]);
    }
}
