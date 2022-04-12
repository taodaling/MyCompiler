package org.dalingtao.parser;

import org.dalingtao.IOUtil;
import org.dalingtao.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//A
//| B C D
//| A C D
public abstract class BaseParserCompiler {
    List<Terminal> terminals = new ArrayList<>();
    List<NonTerminal> nonTerminals = new ArrayList<>();
    List<Production> productions = new ArrayList<>();
    Map<String, Symbol> terminalRegistry = new HashMap<>();


    Symbol getSymbol(String s) {
        return terminalRegistry.computeIfAbsent(s, x -> {
            Terminal t = new Terminal();
            t.name = x;
            terminals.add(t);
            return t;
        });
    }

    public BaseParserCompiler(InputStream is) throws IOException {
        load(is);
        calcNullable();
        calcFirst();
        calcFollow();
        for (int i = 0; i < terminals.size(); i++) {
            terminals.get(i).id = i;
        }
        for (int i = 0; i < nonTerminals.size(); i++) {
            nonTerminals.get(i).id = i + terminals.size();
        }
        for (int i = 0; i < productions.size(); i++) {
            productions.get(i).id = i;
        }
    }

    void addProduction(NonTerminal nt, Symbol... terminals) {
        Production production = new Production();
        production.left = nt;
        production.right = Arrays.asList(terminals);

        productions.add(production);
        nt.productions.add(production);
    }

    void load(InputStream is) throws IOException {
        List<List<String>> lines = IOUtil.readLines(is, StandardCharsets.ISO_8859_1, true)
                .stream().filter(x -> !x.startsWith("#")).map(StringUtil::split).collect(Collectors.toList());
        lines.stream().filter(x -> !x.get(0).equals("|"))
                .forEach(x -> {
                    String name = x.get(0);
                    NonTerminal nt = new NonTerminal();
                    nt.name = name;

                    nonTerminals.add(nt);
                    terminalRegistry.put(nt.name, nt);
                });

        {
            NonTerminal nt = new NonTerminal();
            nt.name = "PROGRAM$";
            nonTerminals.add(nt);
            terminalRegistry.put(nt.name, nt);

            addProduction(nt, terminalRegistry.get("PROGRAM"), getSymbol("eof"));
        }

        NonTerminal built = null;
        for (List<String> line : lines) {
            if (!line.get(0).equals("|")) {
                if (line.size() != 1) {
                    throw new ParserException("E\n| E + E \n| num");
                }
                built = (NonTerminal) terminalRegistry.get(line.get(0));
                continue;
            }
            if (built == null) {
                throw new ParserException("E\n| E + E \n| num");
            }
            addProduction(built, line.stream().skip(1).map(this::getSymbol).toArray(n -> new Symbol[n]));
        }

    }

    public boolean nullable(List<Symbol> symbols) {
        boolean nullable = true;
        for (Symbol s : symbols) {
            nullable &= s.nullable();
        }
        return nullable;
    }

    public Set<Symbol> first(List<Symbol> symbols) {
        Set<Symbol> ans = new HashSet<>();
        for (Symbol s : symbols) {
            ans.addAll(s.first());
            if (!s.nullable()) {
                break;
            }
        }
        return ans;
    }

    void calcNullable() {
        boolean ok = true;
        while (ok) {
            ok = false;
            for (Production p : productions) {
                if (p.left.nullable) {
                    continue;
                }
                boolean allNullable = true;
                for (Symbol s : p.right) {
                    allNullable = allNullable && s.nullable();
                }
                if (allNullable) {
                    p.left.nullable = true;
                    ok = true;
                }
            }
        }
    }

    void calcFirst() {
        boolean ok = true;
        while (ok) {
            ok = false;
            for (Production p : productions) {
                for (Symbol s : p.right) {
                    addAll(p.left.first, s.first());
                    if (!s.nullable()) {
                        break;
                    }
                }
            }
        }
    }


    boolean ok;

    <T> void addAll(Collection<T> a, Collection<T> b) {
        int size = a.size();
        a.addAll(b);
        ok |= size < a.size();
    }

    void calcFollow() {
        ok = true;
        while (ok) {
            ok = false;
            for (Production p : productions) {
                Symbol X = p.left;
                int k = p.right.size();
                for (int i = 0; i < k; i++) {
                    boolean nullable = true;
                    Symbol si = p.right.get(i);
                    for (int j = i + 1; j < k && nullable; j++) {
                        Symbol sj = p.right.get(j);
                        addAll(si.follow(), sj.first());
                        nullable = sj.nullable();
                    }
                    if (nullable) {
                        addAll(si.follow(), X.follow());
                    }
                }
            }
        }
    }

    public abstract void compile(OutputStream os);
}
