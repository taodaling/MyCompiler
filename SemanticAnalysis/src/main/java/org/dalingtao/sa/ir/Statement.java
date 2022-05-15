package org.dalingtao.sa.ir;

public abstract class Statement extends Instruction {
    public abstract CanonicalStatement lowering();

    public Statement(Object... args) {
        super(args);
    }
}
