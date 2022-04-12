package org.dalingtao.parser;

import org.dalingtao.ast.AstNode;
import org.dalingtao.ast.DebugAstNode;

import java.util.ArrayList;
import java.util.List;

public class Production {
    int id;
    NonTerminal left;
    List<Symbol> right = new ArrayList<>();

    AstNode create(AstNode... nodes) {
        var node = new DebugAstNode();
        node.make(nodes);
        return node;
    }

    @Override
    public String toString() {
        return left + " -> " + right.toString();
    }
}
