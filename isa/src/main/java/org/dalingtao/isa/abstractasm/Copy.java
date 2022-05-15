package org.dalingtao.isa.abstractasm;

public class Copy extends Ins {
    public Copy(String... data) {
        super(data);
        setUse(data[2]);
        setDef(data[1]);
    }
}
