package org.dalingtao;

public class StringReader {
    String s;
    int offset;
    Runnable pretask;

    public StringReader(String s) {
        this(s, false);
    }

    public StringReader(String s, boolean skipBlank) {
        this.s = s;
        if (skipBlank) {
            pretask = this::skipBlank;
        } else {
            pretask = () -> {
            };
        }
    }

    public int read() {
        pretask.run();
        if (offset >= s.length()) {
            return -1;
        }
        return s.charAt(offset++);
    }

    public void skipBlank() {
        while (offset < s.length() && Character.isWhitespace(s.charAt(offset))) {
            offset++;
        }
    }

    public int peek() {
        pretask.run();
        if (offset >= s.length()) {
            return -1;
        }
        return s.charAt(offset);
    }

    @Override
    public String toString() {
        return PromptMessage.message(s, offset, offset);
    }
}
