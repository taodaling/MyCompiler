package org.dalingtao;

public class PromptMessage {
    public static String message(String text, int from, int end) {
        int begin = Math.max(0, from - 100);
        int to = Math.min(end + 100, text.length());
        return text.substring(begin, from) + "\n??\n" + text.substring(from, end) + "\n??\n" + text.substring(end, to);
    }
}
