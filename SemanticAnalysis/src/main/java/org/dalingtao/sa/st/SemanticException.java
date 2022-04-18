package org.dalingtao.sa.st;

import org.dalingtao.InputException;
import org.dalingtao.ast.AstNode;

public class SemanticException extends InputException {
    public SemanticException(String message, AstNode node) {
        super(message, node.getBegin(), node.getEnd());
    }
}
