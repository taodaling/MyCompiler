package org.dalingtao.sa.ir;

public class Eseq extends Expression {
    Statement s;
    Expression e;

    public Eseq(Statement s, Expression e) {
        super(s, e);
        this.s = s;
        this.e = e;
    }


    @Override
    public CanonicalExpression lowering() {
        var el = e.lowering();
        return new CanonicalExpression(s.lowering().add(el.s), el.e);
    }
}
