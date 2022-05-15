package org.dalingtao.sa.ir;

public class Name extends Expression {
    String label;

    public String getLabel() {
        return label;
    }

    public Name(String label) {
        super(label);
        this.label = label;
    }


    @Override
    public CanonicalExpression lowering() {
        return new CanonicalExpression(new CanonicalStatement(), this);
    }
}
