package org.dalingtao.sa.ir;

public class Mem extends Expression {
    Expression e;

    public Mem(Expression e) {
        super(e);
        this.e = e;
    }

    @Override
    public CanonicalExpression lowering() {
        var res = e.lowering();
        return new CanonicalExpression(res.s, new Mem(res.e));
    }
}
