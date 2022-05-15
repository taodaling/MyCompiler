package org.dalingtao.sa.ir;

public class Temp extends Expression {
    String id;

    public Temp(int id) {
        super("T" + id);
        this.id = (String) args[0];
    }

    public String getId() {
        return id;
    }

    @Override
    public CanonicalExpression lowering() {
        return new CanonicalExpression(new CanonicalStatement(), this);
    }
}
