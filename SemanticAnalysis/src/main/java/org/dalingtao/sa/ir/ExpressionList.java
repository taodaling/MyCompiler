package org.dalingtao.sa.ir;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList extends Expression {
    List<Expression> list = new ArrayList<>();

    public List<Expression> getList() {
        return list;
    }

    @Override
    public CanonicalExpression lowering() {
        throw new UnsupportedOperationException();
    }
}
