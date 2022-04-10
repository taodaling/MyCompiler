package org.dalingtao.lexer;

public class Token {
    String token;
    String body;

    public Token(String token, String body) {
        this.token = token;
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return getToken() + "(" + getBody() + ")";
    }
}
