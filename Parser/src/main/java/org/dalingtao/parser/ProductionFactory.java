package org.dalingtao.parser;

import org.dalingtao.ast.AstNode;

import java.util.function.Supplier;

public class ProductionFactory extends Production {
    Supplier<AstNode> supplier;

    public void setType(String cls) {
        try {
            supplier = (Supplier) Class.forName(cls).getConstructor().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

    AstNode create(AstNode... nodes) {
        AstNode node = supplier.get();
        node.make(nodes);
        return node;
    }
}
