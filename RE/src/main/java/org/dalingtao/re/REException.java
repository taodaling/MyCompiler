package org.dalingtao.re;

import org.dalingtao.InputException;

public class REException extends RuntimeException {
    public REException(String msg) {
        super("Regular expression error: " + msg);
    }
}
