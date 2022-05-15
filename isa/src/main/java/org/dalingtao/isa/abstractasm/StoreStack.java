package org.dalingtao.isa.abstractasm;

public class StoreStack extends Ins {
    public StoreStack(String offset, String var) {
        super("mov", "[rsp+" + offset + "]", var);
        setUse(var);
    }
}
