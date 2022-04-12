package org.dalingtao.parser;

import org.dalingtao.lexer.Token;

public class TerminalToken {
    Terminal terminal;
    Token token;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }


    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.toString();
    }
}
