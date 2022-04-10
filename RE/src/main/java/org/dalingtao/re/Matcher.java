package org.dalingtao.re;

public interface Matcher {
    void consume(int c);

    boolean match();

    default boolean match(CharSequence s) {
        for (int i = 0; i < s.length(); i++) {
            consume(s.charAt(i));
        }
        return match();
    }
}
