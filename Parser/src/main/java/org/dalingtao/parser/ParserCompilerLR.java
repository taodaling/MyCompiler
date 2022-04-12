package org.dalingtao.parser;

import org.dalingtao.IOUtil;
import org.dalingtao.re.ParseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParserCompilerLR extends BaseParserCompiler {
    public ParserCompilerLR(InputStream is) throws IOException {
        super(is);
        E = new ArrayList<>();
        R = new ArrayList<>();
        calcET();
        calcR();
    }

    Map<List<Item>, Integer> itemMap = new HashMap<>();
    List<List<Item>> items = new ArrayList<>();

    int getItemId(Set<Item> data) {
        List<Item> list = data.stream().collect(Collectors.toList());
        list.sort(Comparator.naturalOrder());
        Integer id = itemMap.get(list);
        if (id == null) {
            id = items.size();
            items.add(list);
            itemMap.put(list, id);
            E.add(new int[nonTerminals.size() + terminals.size()]);
            Arrays.fill(E.get(E.size() - 1), -1);
            R.add(new Production[nonTerminals.size() + terminals.size()]);
        }
        return id;
    }

    void setId(Item item) {
        item.val = item.offset;
        item.val = item.val * productions.size() + item.p.id;
        item.val = item.val * terminals.size() + item.next.id();
    }

    Item makeItem(int offset, Production p, Symbol s) {
        Item item = new Item();
        item.offset = offset;
        item.p = p;
        item.next = s;
        setId(item);
        return item;
    }

    int closure(Set<Item> set) {
        while (true) {
            Set<Item> target = new HashSet<>(set);
            for (Item i : set) {
                var s = i.nextSymbol();
                if (s instanceof NonTerminal) {
                    List<Symbol> follower = new ArrayList<>();
                    for (int j = i.offset + 1; j < i.p.right.size(); j++) {
                        follower.add(i.p.right.get(j));
                    }
                    follower.add(i.next);
                    var first = first(follower);

                    var nt = (NonTerminal) s;
                    for (Production p : nt.productions) {
                        for (var w : first) {
                            target.add(makeItem(0, p, w));
                        }
                    }
                }
            }
            if (target.size() == set.size()) {
                break;
            }
            set = target;
        }
        return getItemId(set);
    }

    int go(int I, Symbol x) {
        Set<Item> J = new HashSet<>();
        for (var item : items.get(I)) {
            if (item.nextSymbol() == x) {
                J.add(makeItem(item.offset + 1, item.p, item.next));
            }
        }
        return closure(J);
    }

    List<int[]> E;
    List<Production[]> R;

    void calcET() {
        Symbol eof = getSymbol("eof");
        closure(Collections.singleton(makeItem(0, ((NonTerminal) getSymbol("PROGRAM$")).productions.get(0), getSymbol("eof"))));
        while (true) {
            int curSize = items.size();
            boolean ok = false;
            for (int i = 0; i < items.size(); i++) {
                List<Item> I = items.get(i);
                Set<Symbol> follower = new HashSet<>();
                for (var item : I) {
                    var symbol = item.nextSymbol();
                    if (symbol != null) {
                        follower.add(symbol);
                    }
                }
                follower.remove(eof);
                for (var s : follower) {
                    if (E.get(i)[s.id()] == -1) {
                        E.get(i)[s.id()] = go(i, s);
                    }
                }
            }
            if (curSize == items.size() && !ok) {
                break;
            }
        }
    }

    void calcR() {
        for (int i = 0; i < items.size(); i++) {
            var I = items.get(i);
            for (var item : I) {
                if (item.nextSymbol() == null) {
                    if (R.get(i)[item.next.id()] != null) {
                        throw new ParseException("Invalid LR(1) grammar : duplicate reduce command");
                    }
                    R.get(i)[item.next.id()] = item.p;
                }
            }
        }
    }

    @Override
    public void compile(OutputStream os) {
        int m = nonTerminals.size();
        int n = terminals.size();
        //output
        PrintStream ps = new PrintStream(os);
        ps.print(m);
        ps.print(" ");
        ps.print(n);
        ps.print(" ");
        ps.print(productions.size());
        ps.print(" ");
        ps.println(items.size());
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
        //0 fail
        //1 shift J
        //2 goto J
        //3 reduce n
        //4 accept
        //make table
        Production targetProd = ((NonTerminal) getSymbol("PROGRAM$")).productions.get(0);
        Symbol eof = getSymbol("eof");
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            boolean findTarget = item.stream().anyMatch(x -> x.p == targetProd && x.offset == targetProd.right.size() - 1);
            for (int j = 0; j < terminals.size() + nonTerminals.size(); j++) {
                boolean accept = findTarget && eof.id() == j;
                var e = E.get(i)[j];
                var r = R.get(i)[j];
                if (e != -1 && r != null) {
                    throw new ParseException("Invalid LR(1) grammar : collision between goto and reduce command");
                }
                if (accept) {
                    ps.print('a');
                    ps.print("\t");
                } else if (e != -1) {
                    ps.print(j < terminals.size() ? 's' : 'g');
                    ps.print(e);
                    ps.print("\t");
                } else if (r != null) {
                    ps.print('r');
                    ps.print(r.id);
                    ps.print("\t");
                } else {
                    ps.print('f');
                    ps.print("\t");
                }
            }
            ps.println();
        }
        //init state
        ps.println(0);
        ps.flush();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(loadSample());
    }

    public static String loadSample() throws IOException {
        try (var is = ParserCompilerLR.class.getClassLoader().getResourceAsStream("sample.parser")) {
            String content = IOUtil.readAll(is, StandardCharsets.ISO_8859_1);
            System.out.println("sample.parser:\n" + content);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            new ParserCompilerLR(new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1))).compile(bao);
            return bao.toString(StandardCharsets.ISO_8859_1);
        }
    }
}
