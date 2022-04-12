package org.dalingtao.lexer;

public class Token {
    String token;
    String body;
    int from;
    int end;

    public Token(String token, String body, int from, int end) {
        this.token = token;
        this.body = body;
        this.from = from;
        this.end = end;
    }

    public String getToken() {
        return token;
    }

    public String getBody() {
        return body;
    }

    public int getFrom() {
        return from;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return getToken() + "=" + getBody();
    }
}
