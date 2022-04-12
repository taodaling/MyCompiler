package org.dalingtao.parser;

import org.dalingtao.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ParserCompilerLL extends BaseParserCompiler {
    public static void main(String[] args) throws IOException {
        System.out.println(loadSample());
    }


    public ParserCompilerLL(InputStream is) throws IOException {
        super(is);
    }

    public void compile(OutputStream os) {
        int m = nonTerminals.size();
        int n = terminals.size();
        for (int i = 0; i < n; i++) {
            terminals.get(i).id = i;
        }
        for (int i = 0; i < m; i++) {
            nonTerminals.get(i).id = i + n;
        }
        for (int i = 0; i < productions.size(); i++) {
            productions.get(i).id = i;
        }
        //int[][] table = new int[m][n];
        Production[][] table = new Production[m][n];
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                table[i][j] = -1;
//            }
//        }
        for (int i = 0; i < m; i++) {
            NonTerminal nt = nonTerminals.get(i);
            for (Production p : nt.productions) {
                for (var s : first(p.right)) {
                    if (table[i][s.id()] != null) {
                        throw new ParserException("Invalid LL(1) grammar => [" + table[i][s.id()] + "] collide with [" + p + "]");
                    }
                    table[i][s.id()] = p;
                }
                if (nullable(p.right)) {
                    //throw new RuntimeException();
                    for (var s : p.left.follow()) {
                        if (table[i][s.id()] != null) {
                            throw new ParserException("Invalid LL(1) grammar => [" + table[i][s.id()] + "] collide with [" + p + "]");
                        }
                        table[i][s.id()] = p;
                    }
                }
            }
        }
        //output
        PrintStream ps = new PrintStream(os);
        ps.print(m);
        ps.print(" ");
        ps.print(n);
        ps.print(" ");
        ps.print(productions.size());
        ps.println();
        for (var symbol : nonTerminals) {
            ps.print(symbol.name);
            ps.print(" ");
        }
        ps.println();
        for (var symbol : terminals) {
            ps.print(symbol.name);
            ps.print(" ");
        }
        ps.println();
        for (var p : productions) {
            ps.print(p.left.id);
            ps.print(" ");
            ps.print(p.right.size());
            ps.print(" ");
            for (var s : p.right) {
                ps.print(s.id());
                ps.print(" ");
            }
            ps.println();
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ps.print(table[i][j] == null ? -1 : table[i][j].id);
                ps.print(" ");
            }
            ps.println();
        }
        ps.flush();
    }

    public static String loadSample() throws IOException {
        try (var is = ParserCompilerLL.class.getClassLoader().getResourceAsStream("sample.parser")) {
            String content = IOUtil.readAll(is, StandardCharsets.ISO_8859_1);
            System.out.println("sample.parser:\n" + content);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            new ParserCompilerLL(new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1))).compile(bao);
            return bao.toString(StandardCharsets.ISO_8859_1);
        }
    }
}
