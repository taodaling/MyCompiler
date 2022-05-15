package org.dalingtao.sa.ir;

public class Cjump extends Statement {
    Expression e;
    Name t;
    Name f;

    public Cjump(Expression e, Name t, Name f) {
        super(e, t, f);
        this.e = e;
        this.t = t;
        this.f = f;
    }

    public Name getT() {
        return t;
    }

    public Name getF() {
        return f;
    }


    @Override
    public CanonicalStatement lowering() {
        var res = e.lowering();
        return res.s.add(new CanonicalStatement(new Cjump(res.e, t, f)));
    }

    public Cjump negate() {
        return new Cjump(new UnaryOp.LogicNot(e), f, t);
    }
}
