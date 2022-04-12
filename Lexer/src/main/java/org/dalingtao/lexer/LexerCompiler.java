package org.dalingtao.lexer;

import org.dalingtao.IOUtil;
import org.dalingtao.re.LexerDumper;
import org.dalingtao.re.NfaRE;
import org.dalingtao.re.REParser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Comment=//[^\n]*
//# This is the comment
//NUM=DIGIT|[1-9]({DIGIT})+
public class LexerCompiler {
    public static void main(String[] args) throws IOException {
        System.out.println(new String(loadSample(), StandardCharsets.ISO_8859_1));
    }

    public static byte[] loadSample() throws IOException {
        try (var is = LexerCompiler.class.getClassLoader()
                .getResourceAsStream("sample.lexer")) {
            String content = IOUtil.readAll(is, StandardCharsets.ISO_8859_1);
            System.out.println("sample.lexer:\n" + content);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            compile(content, 128, bao);
            return bao.toByteArray();
        }
    }

    public static void compile(String content, int charset, OutputStream os) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(content));
        String line;
        int lineNumber = 0;

        List<Unit> units = new ArrayList<>();
        Set<String> tokeNameSet = new HashSet<>();
        while ((line = br.readLine()) != null) {
            lineNumber++;
            if (line.startsWith("#") || line.isBlank()) {
                continue;
            }
            int index = line.indexOf('=');
            if (index == -1) {
                throw new LexerException("Can't compile " + lineNumber + "-th line");
            }
            String left = line.substring(0, index);
            String right = line.substring(index + 1);
            String tokenName = left;
            if (left.startsWith("@")) {
                tokenName = tokenName.substring(1);
            }
            if (tokeNameSet.contains(tokenName)) {
                throw new LexerException("Duplicate token: " + tokenName);
            }
            Unit unit = new Unit(tokenName, right);
            unit.ignore = left.startsWith("@");
            units.add(unit);
            tokeNameSet.add(unit.token);
        }

        List<NfaRE> res = new ArrayList<>();
        for (Unit unit : units) {
            NfaRE nfa = new REParser().parse(unit.pattern);
            nfa.addPropertyForFinalState("TOKEN", unit.token);
            nfa.addPropertyForFinalState("IGNORE", unit.ignore);
            res.add(nfa);
        }

        var dfa = NfaRE.merge(res.toArray(new NfaRE[0])).toDfa(charset);
        LexerDumper.dump(dfa, os);
    }

    static class Unit {
        String token;
        String pattern;
        boolean ignore;

        public Unit(String token, String pattern) {
            this.token = token;
            this.pattern = pattern;
        }
    }
}
