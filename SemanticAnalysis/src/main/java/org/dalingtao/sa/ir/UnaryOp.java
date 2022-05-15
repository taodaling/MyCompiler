package org.dalingtao.sa.ir;

public abstract class UnaryOp extends Expression {
    Expression e;

    public UnaryOp(Expression e) {
        super(e);
        this.e = e;
    }

    public static class LogicNot extends UnaryOp {
        public LogicNot(Expression e) {
            super(e);
        }

        @Override
        public CanonicalExpression lowering() {
            var res = e.lowering();
            res.e = new LogicNot(res.e);
            return res;
        }
    }

}
