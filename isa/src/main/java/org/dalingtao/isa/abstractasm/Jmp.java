package org.dalingtao.isa.abstractasm;

public class Jmp extends Ins {
    public Jmp(String... data) {
        super(data);
    }

    public String getTarget() {
        return data[1];
    }
}
