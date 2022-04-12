package org.dalingtao.lexer;

import java.util.List;

public class TokenSequence {
    List<Token> tokens;
    String text;

    public TokenSequence(List<Token> tokens, String text) {
        this.tokens = tokens;
        this.text = text;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getText() {
        return text;
    }
}
