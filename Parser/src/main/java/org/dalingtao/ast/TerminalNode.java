package org.dalingtao.ast;

import org.dalingtao.parser.TerminalToken;

public class TerminalNode extends AstNode {
    TerminalToken token;

    public TerminalNode(TerminalToken token) {
        this.token = token;
        begin = token.getToken().getFrom();
        end = token.getToken().getEnd();
    }

    @Override
    public void make(AstNode... children) {
        super.make(children);
    }

    @Override
    public String toString() {
        return "<T>" + token.toString() + "</T>";
    }

    @Override
    public String getTerminalBody() {
        return token.getToken().getBody();
    }
}
