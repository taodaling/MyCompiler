package org.dalingtao.lexer;

import org.dalingtao.Context;
import org.dalingtao.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static void main(String[] args) throws IOException {
        List<Token> data = loadSample().tokens;
        System.out.println(data);
    }

    public static TokenSequence loadSample() throws IOException {
        byte[] data = LexerCompiler.loadSample();
        var lexer = new Lexer(new ByteArrayInputStream(data));
        try (var is = Lexer.class.getClassLoader().getResourceAsStream("sample.code")) {
            String content = IOUtil.readAll(is, StandardCharsets.ISO_8859_1);
            System.out.println("sample.code:\n" + content);
            return lexer.lexer(content, true);
        }
    }

    public static Lexer fromRule(String content) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        LexerCompiler.compile(content, 128, bao);
        return new Lexer(new ByteArrayInputStream(bao.toByteArray()));
    }


    LexerDfa dfa;

    public Lexer(InputStream is) throws IOException {
        dfa = new LexerDfa(is);
    }

    public TokenSequence lexer(String data) {
        return lexer(data, true);
    }

    public TokenSequence lexer(String data, boolean skipIgnore) {
        Context.getInstance().setCode(data);
        List<Token> ans = new ArrayList<>();
        int n = data.length();
        for (int i = 0; i < n; i++) {
            String lastToken = null;
            int index = -1;
            boolean ignore = false;
            var matcher = dfa.matcher();
            for (int j = i; j < n && !matcher.isStop(); j++) {
                char c = data.charAt(j);
                matcher.consume(c);
                String cand = matcher.token();
                if (cand != null) {
                    lastToken = cand;
                    ignore = matcher.ignore();
                    index = j;
                }
            }
            if (lastToken == null) {
                throw new LexerException(i, Math.min(i + 10, data.length()));
            }
            if (!(ignore && skipIgnore)) {
                ans.add(new Token(lastToken, data.substring(i, index + 1), i, index + 1));
            }
            i = index;
        }
        ans.add(new Token("eof", "", n, n));
        return new TokenSequence(ans, data);
    }
}
