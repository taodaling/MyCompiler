package org.dalingtao.ast;

import org.dalingtao.parser.TerminalToken;

public class TerminalNode implements AstNode {
    TerminalToken token;

    public TerminalNode(TerminalToken token) {
        this.token = token;
    }

    @Override
    public Object eval(Context context) {
        return token.getBody();
    }

    @Override
    public void make(AstNode... children) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "<T>" + token.getBody() + "</T>";
    }
}
