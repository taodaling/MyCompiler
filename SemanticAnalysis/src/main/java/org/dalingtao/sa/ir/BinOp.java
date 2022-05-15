package org.dalingtao.sa.ir;

import org.dalingtao.Context;

public abstract class BinOp extends Expression implements Cloneable {
    Expression a;
    Expression b;


    public Expression getA() {
        return a;
    }

    public Expression getB() {
        return b;
    }


    public BinOp(Expression a, Expression b) {
        super(a, b);
        this.a = a;
        this.b = b;
    }

    @Override
    public BinOp clone() {
        try {
            BinOp res = (BinOp) super.clone();
            res.args = res.args.clone();
            return res;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CanonicalExpression lowering() {
        var aRes = a.lowering();
        var bRes = b.lowering();
        CanonicalStatement s = new CanonicalStatement();
        s.add(aRes.s);
        Temp temp = new Temp(Context.getInstance().nextId());
        s.add(new Move.Temp(temp, aRes.e));
        s.add(bRes.s);

        //TODO optimize here for better understanding
        BinOp clone = clone();
        clone.args[0] = temp;
        clone.args[1] = bRes.e;
        clone.a = temp;
        clone.b = bRes.e;
        return new CanonicalExpression(s, clone);
    }

    public static class Add extends BinOp {
        public Add(Expression a, Expression b) {
            super(a, b);
        }
    }


    public static class Sub extends BinOp {
        public Sub(Expression a, Expression b) {
            super(a, b);
        }

    }

    public static class Mul extends BinOp {
        public Mul(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Div extends BinOp {
        public Div(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class LogicAnd extends BinOp {
        public LogicAnd(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class LogicOr extends BinOp {
        public LogicOr(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Eq extends BinOp {
        public Eq(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Neq extends BinOp {
        public Neq(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Lt extends BinOp {
        public Lt(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Ult extends BinOp {
        public Ult(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Gt extends BinOp {
        public Gt(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Leq extends BinOp {
        public Leq(Expression a, Expression b) {
            super(a, b);
        }
    }

    public static class Geq extends BinOp {
        public Geq(Expression a, Expression b) {
            super(a, b);
        }
    }
}
