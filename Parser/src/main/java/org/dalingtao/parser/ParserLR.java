package org.dalingtao.parser;

import org.dalingtao.Context;
import org.dalingtao.PromptMessage;
import org.dalingtao.StreamReader;
import org.dalingtao.ast.AstNode;
import org.dalingtao.ast.TerminalNode;
import org.dalingtao.lexer.Lexer;
import org.dalingtao.lexer.LexerCompiler;
import org.dalingtao.lexer.TokenSequence;
import org.dalingtao.re.ParseCompileException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParserLR {
    char[][] cmd;
    int[][] jump;
    Terminal[] terminals;
    NonTerminal[] nonTerminals;
    ProductionFactory[] production;
    Map<String, Terminal> terminalMap;
    int n;
    int startState;
    Map<String, String> properties;

    public static ParserLR fromRule(String content) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        new ParserCompilerLR(new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1)))
                .compile(bao);
        return new ParserLR(new ByteArrayInputStream(bao.toByteArray()));
    }

    public ParserLR(InputStream is) throws IOException, ClassNotFoundException {
        StreamReader sr = new StreamReader(new InputStreamReader(is, StandardCharsets.ISO_8859_1));
        int m = sr.readInt();
        n = sr.readInt();
        int k = sr.readInt();
        int numState = sr.readInt();
        cmd = new char[numState][n + m];
        jump = new int[numState][n + m];
        terminals = new Terminal[n];
        nonTerminals = new NonTerminal[m];
        production = new ProductionFactory[k];
        int numProperties = sr.readInt();
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
        for (int i = 0; i < numState; i++) {
            for (int j = 0; j < n + m; j++) {
                String B = sr.read();
                cmd[i][j] = B.charAt(0);
                if (B.length() > 1) {
                    jump[i][j] = Integer.parseInt(B.substring(1));
                }
            }
        }
        startState = sr.readInt();
    }

    void throwException(TerminalToken tt) {
        throw new ParseException(tt.token);
    }

    public AstNode parse(TokenSequence tokenSequence) {
        Context.getInstance().setCode(tokenSequence.getText());

        List<TerminalToken> tts = tokenSequence.getTokens().stream().map(token -> {
            TerminalToken tt = new TerminalToken();
            tt.token = token;
            tt.terminal = terminalMap.get(token.getToken());
            if (tt.terminal == null) {
                throwException(tt);
            }
            return tt;
        }).collect(Collectors.toList());

        Deque<State> dq = new ArrayDeque<>();
        dq.addLast(new State(0, null, null));
        for (var tt : tts) {
            AstNode node = new TerminalNode(tt);
            while (node != null) {
                switch (cmd[dq.getLast().state][tt.terminal.id]) {
                    case 'f':
                        throwException(tt);
                    case 's':
                        dq.addLast(new State(jump[dq.getLast().state][tt.terminal.id], node, tt.terminal));
                        node = null;
                        break;
                    case 'r':
                        ProductionFactory rule = production[jump[dq.getLast().state][tt.terminal.id]];
                        AstNode[] nodes = new AstNode[rule.right.size()];
                        for (int i = rule.right.size() - 1; i >= 0; i--) {
                            nodes[i] = dq.removeLast().node;
                        }
                        AstNode merged = rule.create(nodes);
                        if (cmd[dq.getLast().state][rule.left.id] != 'g') {
                            throwException(tt);
                        }
                        dq.addLast(new State(jump[dq.getLast().state][rule.left.id], merged, rule.left));
                        break;
                    case 'a':
                        return dq.getLast().node;
                }
            }
        }
        //impossible here
        throw new IllegalStateException();
        //throw new ParseCompileException("Syntax error: can't find eof");
    }

    static class State {
        int state;
        AstNode node;
        Symbol symbol;

        public State(int state, AstNode node, Symbol symbol) {
            this.state = state;
            this.node = node;
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return "" + node;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(loadSample());
    }

    public static AstNode loadSample() throws IOException, ClassNotFoundException {
        String grammar = ParserCompilerLR.loadSample();
        ParserLR parser = new ParserLR(new ByteArrayInputStream(grammar.getBytes(StandardCharsets.ISO_8859_1)));
        var ans = parser.parse(Lexer.loadSample());
        return ans;
    }
}
