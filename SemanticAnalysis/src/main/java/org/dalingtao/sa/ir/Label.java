package org.dalingtao.sa.ir;

import org.dalingtao.sa.st.FunctionDef;

public class Label extends Statement {
    String name;


    public Label(int id) {
        this("." + id);
    }

    public Label(String s) {
        super(s);
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public Name toName() {
        return new Name(name);
    }


    @Override
    public CanonicalStatement lowering() {
        return new CanonicalStatement(this);
    }
}
