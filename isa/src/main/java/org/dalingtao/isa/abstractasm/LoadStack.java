package org.dalingtao.isa.abstractasm;

public class LoadStack extends Ins {
    //offset from top of stack
    public LoadStack(String offset, String var) {
        super("mov", var, "[rsp+" + offset + "]");
        setDef(var);
    }
}
