package org.dalingtao.sa.ir;

public class Jump extends Statement {
    Name label;

    public Jump(Name label) {
        super(label);
        this.label = label;
    }

    public Name getLabel() {
        return label;
    }

    @Override
    public CanonicalStatement lowering() {
        var res = label.lowering();
        return res.s.add(new Jump((Name) res.e));
    }


}
