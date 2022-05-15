package org.dalingtao.sa.ir;

public class Const extends Expression {
    String val;

    public Const(int val) {
        super("" + val);
        this.val = (String) args[0];
    }


    public String getVal() {
        return val;
    }

    @Override
    public CanonicalExpression lowering() {
        return new CanonicalExpression(new CanonicalStatement(), this);
    }
}
