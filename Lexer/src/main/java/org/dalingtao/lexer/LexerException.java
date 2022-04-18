package org.dalingtao.lexer;

import org.dalingtao.InputException;

public class LexerException extends InputException {
    public LexerException(int begin, int end) {
        super("Lexer error", begin, end);
    }
}
