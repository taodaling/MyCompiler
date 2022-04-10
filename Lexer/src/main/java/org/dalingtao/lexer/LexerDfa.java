package org.dalingtao.lexer;

import org.dalingtao.StreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LexerDfa {
    String[] tokens;
    boolean[] ignores;
    int[][] adj;
    int n;
    int m;

    public LexerDfa(InputStream is) throws IOException {
        StreamReader reader = new StreamReader(new InputStreamReader(is));
        n = reader.readInt();
        m = reader.readInt();
        tokens = new String[n];
        ignores = new boolean[n];
        adj = new int[n][m];
        for (int i = 0; i < n; i++) {
            int t = reader.readInt();
            if (t == 1) {
                tokens[i] = reader.read();
                ignores[i] = reader.readInt() == 0;
            }
            for (int j = 0; j < m; j++) {
                adj[i][j] = reader.readInt();
            }
        }
    }

    public Matcher matcher() {
        return new Matcher();
    }

    public class Matcher {
        int cur = 1;

        boolean isStop() {
            return cur == 0;
        }

        boolean isStart() {
            return cur == 1;
        }

        String token() {
            return tokens[cur];
        }

        boolean ignore() {
            return ignores[cur];
        }

        void consume(int c) {
            if (c < 0 || c >= m) {
                cur = 0;
            } else {
                cur = adj[cur][c];
            }
        }
    }

}
