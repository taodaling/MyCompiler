package org.dalingtao.sa.ir;

public class Seq extends Statement {
    Statement[] s;

    public Seq(Statement... s) {
        super((Object[]) s);
        this.s = s;
    }


    @Override
    public CanonicalStatement lowering() {
        CanonicalStatement all = new CanonicalStatement();
        for (Statement st : s) {
            all.add(st.lowering());
        }
        return all;
    }
}
