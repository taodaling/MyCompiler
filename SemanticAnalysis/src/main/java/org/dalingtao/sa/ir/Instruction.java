package org.dalingtao.sa.ir;

import org.dalingtao.ToStringXml;

import java.util.Arrays;

public abstract class Instruction {

    Object[] args;

    public Instruction(Object... args) {
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return ToStringXml.toString(getClass().getSimpleName(), Arrays.asList(args));
    }

    public abstract CanonicalInstruction lowering();
}
