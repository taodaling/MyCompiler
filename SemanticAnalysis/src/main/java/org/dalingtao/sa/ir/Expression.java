package org.dalingtao.sa.ir;

public abstract class Expression extends Instruction {
    public abstract CanonicalExpression lowering();

    public Expression(Object... args) {
        super(args);
    }
}
