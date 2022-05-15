package org.dalingtao.isa.abstractasm;

public class Ret extends Ins {
    public Ret() {
        super("ret");
        setUse(Registers.rax);
    }

    @Override
    public String toASM() {
        return "leave\nret";
    }
}
