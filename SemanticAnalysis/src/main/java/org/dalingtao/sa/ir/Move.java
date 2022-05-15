package org.dalingtao.sa.ir;

import org.dalingtao.Context;

public abstract class Move extends Statement {
    Expression lhs;
    Expression rhs;

    public Move(Expression lhs, Expression rhs) {
        super(lhs, rhs);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public static class Temp extends Move {
        public Temp(org.dalingtao.sa.ir.Temp lhs, Expression rhs) {
            super(lhs, rhs);
        }

        @Override
        public CanonicalStatement lowering() {
            var rhsRes = rhs.lowering();
            return rhsRes.s.add(new Temp((org.dalingtao.sa.ir.Temp) lhs, rhsRes.e));
        }
    }

    public static class Mem extends Move {
        public Mem(org.dalingtao.sa.ir.Mem lhs, Expression rhs) {
            super(lhs, rhs);
        }

        @Override
        public CanonicalStatement lowering() {
            var lhsRes = lhs.lowering();
            var rhsRes = rhs.lowering();
            org.dalingtao.sa.ir.Temp temp = new org.dalingtao.sa.ir.Temp(Context.getInstance().nextId());
            return lhsRes.s.add(new Temp(temp, lhsRes.e))
                    .add(rhsRes.s).add(new Mem(new org.dalingtao.sa.ir.Mem(temp), rhsRes.e));
        }
    }

}
