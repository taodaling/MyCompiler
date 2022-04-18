package org.dalingtao;

public class InputException extends RuntimeException {
    public InputException(String message, int begin, int end) {
        super(message + ": " + PromptMessage.message(Context.getInstance().getCode(), begin, end));
    }
}
