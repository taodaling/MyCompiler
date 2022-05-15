package org.dalingtao.isa.abstractasm;

public class Label extends Ins {
    public Label(String... data) {
        super(data);
    }

    @Override
    public String toString() {
        return data[0] + ":";
    }

    public String getLabel() {
        return data[0];
    }
}
