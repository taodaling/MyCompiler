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
        if (!hasMore()) {
            throw new IOException("No more token");
        }
        return tokenizer.nextToken();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(read());
    }

    public boolean hasMore() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = is.readLine();
            if (line == null) {
                return false;
            }
            tokenizer = new StringTokenizer(line);
        }
        return true;
    }
}