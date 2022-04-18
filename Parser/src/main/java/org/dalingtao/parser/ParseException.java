package org.dalingtao.parser;

import org.dalingtao.InputException;
import org.dalingtao.lexer.Token;

public class ParseException extends InputException {
    public ParseException(Token token) {
        super("Can't parse input", token.getFrom(), token.getEnd());
    }
}
