package org.dalingtao.sa.ir;

public class Exp extends Statement {
    Expression e;

    public Exp(Expression e) {
        super(e);
        this.e = e;
    }


    @Override
    public CanonicalStatement lowering() {
        return e.lowering().s;
    }
}
