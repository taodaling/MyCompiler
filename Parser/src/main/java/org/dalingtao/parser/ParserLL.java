package org.dalingtao.parser;

import org.dalingtao.Context;
import org.dalingtao.PromptMessage;
import org.dalingtao.StreamReader;
import org.dalingtao.ast.AstNode;
import org.dalingtao.ast.TerminalNode;
import org.dalingtao.lexer.Lexer;
import org.dalingtao.lexer.TokenSequence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParserLL {
    int[][] table;
    Terminal[] terminals;
    NonTerminal[] nonTerminals;
    ProductionFactory[] production;
    Map<String, Terminal> terminalMap;
    Map<String, String> properties;
    int n;

    public ParserLL(InputStream is) throws IOException {
        StreamReader sr = new StreamReader(new InputStreamReader(is, StandardCharsets.ISO_8859_1));
        int m = sr.readInt();
        n = sr.readInt();
        int k = sr.readInt();
        int numProperties = sr.readInt();
        table = new int[m][n];
        terminals = new Terminal[n];
        nonTerminals = new NonTerminal[m];
        production = new ProductionFactory[k];
        properties = new HashMap<>(numProperties);
        for (int i = 0; i < numProperties; i++) {
            properties.put(sr.read(), sr.read());
        }
        String classFile = properties.get("classFile");

        for (int i = 0; i < m; i++) {
            nonTerminals[i] = new NonTerminal();
            nonTerminals[i].name = sr.read();
            nonTerminals[i].id = i + n;
        }
        terminalMap = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            terminals[i] = new Terminal();
            terminals[i].name = sr.read();
            terminals[i].id = i;
            terminalMap.put(terminals[i].name, terminals[i]);
        }
        for (int i = 0; i < k; i++) {
            production[i] = new ProductionFactory();
            production[i].left = nonTerminals[sr.readInt() - n];
            production[i].left.productions.add(production[i]);
            int num = sr.readInt();
            production[i].right = new ArrayList<>(num);
            for (int j = 0; j < num; j++) {
                int id = sr.readInt();
                if (id < n) {
                    production[i].right.add(terminals[id]);
                } else {
                    production[i].right.add(nonTerminals[id - n]);
                }
            }
            production[i].setType(classFile + "$" + production[i].left.name + (production[i].left.productions.size() - 1));
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = sr.readInt();
            }
        }
    }


    public AstNode parse(TokenSequence tokenSequence) {
        Context.getInstance().setCode(tokenSequence.getText());
        Parser parser = new Parser();
        parser.text = tokenSequence.getText();
        parser.tokens = tokenSequence.getTokens().stream().map(token -> {
            TerminalToken tt = new TerminalToken();
            tt.token = token;
            tt.terminal = terminalMap.get(token.getToken());
            if (tt.terminal == null) {
                throw new ParseException(token);
            }
            return tt;
        }).collect(Collectors.toList());
        //System.out.println(parser.tokens);

        Symbol prog = Arrays.stream(nonTerminals).filter(x -> x.name().equals("PROGRAM$")).findFirst().orElseThrow();
        return parser.dfs(prog).get(0);
    }

    public class Parser {
        List<TerminalToken> tokens;
        int offset;
        String text;

        TerminalToken read() {
            return tokens.get(offset++);
        }

        TerminalToken peek() {
            return tokens.get(offset);
        }

        void assertTrue(Predicate<TerminalToken> predicate) {
            if (!predicate.test(peek())) {
                throwException();
            }
        }

        void throwException() {
            throw new ParseException(peek().token);
        }


        AstNode dfs(Symbol s) {
            if (s.getClass() == Terminal.class) {
                assertTrue(x -> x.terminal == s);
                return new TerminalNode(read());
            }
            NonTerminal nt = (NonTerminal) s;
            int pid = table[nt.id() - n][peek().terminal.id()];
            if (pid == -1) {
                throwException();
            }
            ProductionFactory p = production[pid];
            return p.create(p.right.stream().map(this::dfs).toArray(n -> new AstNode[n]));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(loadSample());
    }

    public static AstNode loadSample() throws IOException, ClassNotFoundException {
        String grammar = ParserCompilerLL.loadSample();
        ParserLL parser = new ParserLL(new ByteArrayInputStream(grammar.getBytes(StandardCharsets.ISO_8859_1)));
        var ans = parser.parse(Lexer.loadSample());
        return ans;
    }
}
