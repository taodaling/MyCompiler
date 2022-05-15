package org.dalingtao.sa.ir;

public class Nil extends Statement {


    @Override
    public CanonicalStatement lowering() {
        return new CanonicalStatement();
    }
}
