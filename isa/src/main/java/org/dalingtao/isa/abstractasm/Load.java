package org.dalingtao.isa.abstractasm;

public class Load extends Ins {
    public Load(String... data) {
        super(data);
        setDef(data[1]);
        setUse(data[2].substring(1, data[2].length() - 1));
    }
}
