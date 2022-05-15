package org.dalingtao.sa.ir;

import java.util.List;

public class CanonicalExpression extends CanonicalInstruction {
    CanonicalStatement s;
    Expression e;

    public CanonicalExpression(CanonicalStatement s, Expression e) {
        this.s = s;
        this.e = e;
    }

    @Override
    public String toString() {
        List ans = s.s.toList();
        ans.add(e);
        return ans.toString();
    }
}
