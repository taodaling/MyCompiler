package org.dalingtao.parser;

public class TerminalToken {
    Terminal terminal;
    String body;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return terminal.toString() + "=" + body;
    }
}
