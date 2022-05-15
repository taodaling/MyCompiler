package org.dalingtao.isa.abstractasm;

public class Store extends Ins {
    public Store(String... data) {
        super(data);
        setUse(data[1].substring(1, data[1].length() - 1));
        setUse(data[2]);
    }
}
