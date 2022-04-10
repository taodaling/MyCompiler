package org.dalingtao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class StreamReader {
    BufferedReader is;
    StringTokenizer tokenizer;

    public StreamReader(Reader is) {
        this.is = new BufferedReader(is);
    }

    public String read() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(is.readLine());
        }
        return tokenizer.nextToken();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(read());
    }
}