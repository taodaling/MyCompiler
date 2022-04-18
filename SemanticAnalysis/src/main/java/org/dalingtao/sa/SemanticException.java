package org.dalingtao.sa;

import org.dalingtao.InputException;
import org.dalingtao.ast.AstNode;

public class SemanticException extends InputException {
    public SemanticException(AstNode node) {
        super("Semantic error", node.getBegin(), node.getEnd());
    }
}
