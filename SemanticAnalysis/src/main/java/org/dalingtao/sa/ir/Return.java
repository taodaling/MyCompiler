package org.dalingtao.sa.ir;

import org.dalingtao.Context;

public abstract class Return extends Statement {
    String f;

    public Return(Object... args) {
        super(args);
    }

    public String getF() {
        return f;
    }

    public static class ReturnVal extends Return {
        Expression e;

        @Override
        public CanonicalStatement lowering() {
            var el = e.lowering();
            Temp temp = new Temp(Context.getInstance().nextId());
            return el.s.add(new Move.Temp(temp, el.e)).add(new ReturnVal(f, temp));
        }

        public Expression getE() {
            return e;
        }

        public ReturnVal(String f, Expression e) {
            super(e);
            this.f = f;
            this.e = e;
        }
    }
}
