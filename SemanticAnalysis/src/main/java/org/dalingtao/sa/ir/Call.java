package org.dalingtao.sa.ir;

import org.dalingtao.ArrayUtils;
import org.dalingtao.Context;

import java.util.ArrayList;
import java.util.List;

public class Call extends Expression {
    Name f;
    Expression[] a;

    public Name getF() {
        return f;
    }

    public Expression[] getA() {
        return a;
    }

    public Call(Name f, Expression... a) {
        super(ArrayUtils.concat(new Name[]{f}, a));
        this.f = f;
        this.a = a;
    }


    @Override
    public CanonicalExpression lowering() {
        CanonicalStatement ss = new CanonicalStatement();
        List<Expression> es = new ArrayList<>();
        for (Expression e : a) {
            CanonicalExpression ce = e.lowering();
            ss.add(ce.s);
            Temp temp = new Temp(Context.getInstance().nextId());
            ss.add(new Move.Temp(temp, ce.e));
            es.add(temp);
        }
        Temp res = new Temp(Context.getInstance().nextId());
        ss.add(new Move.Temp(res, new Call(f, es.toArray(new Expression[0]))));
        return new CanonicalExpression(ss, res);
    }
}
