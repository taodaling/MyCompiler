package org.dalingtao.re;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class REParser {
    String pattern;
    int offset;
    static int[] escape;

    static {
        escape = new int[128];
        Arrays.fill(escape, -1);
        escape['n'] = '\n';
        escape['r'] = '\r';
        escape['t'] = '\t';
        escape['b'] = '\b';
        escape['f'] = '\f';
    }

    int read() {
        if (offset >= pattern.length()) {
            return -1;
        }
        return pattern.charAt(offset++);
    }

    int peek() {
        if (offset >= pattern.length()) {
            return -1;
        }
        return pattern.charAt(offset);
    }

    List<State> all = new ArrayList<>();

    <T extends BaseState> T create(Supplier<T> supplier) {
        T item = supplier.get();
        item.id = all.size();
        all.add(item);
        return item;
    }

    SubGraph and(SubGraph lhs, SubGraph rhs) {
        lhs.to.set(rhs.start);
        return new SubGraph(lhs.start, rhs.to);
    }

    SubGraph or(SubGraph lhs, SubGraph rhs) {
        BaseState state = create(BaseState::new);
        state.adj.add(new TransferImpl(lhs.start));
        state.adj.add(new TransferImpl(rhs.start));
        return new SubGraph(state, new MultiTransfer(lhs.to, rhs.to));
    }

    SubGraph maybe(SubGraph lhs) {
        BaseState state = create(BaseState::new);
        Transfer t = new TransferImpl(null);
        state.adj.add(new TransferImpl(lhs.start));
        state.adj.add(t);
        return new SubGraph(state, new MultiTransfer(lhs.to, t));
    }

    SubGraph anytime(SubGraph lhs) {
        BaseState state = create(BaseState::new);
        Transfer t = new TransferImpl(null);
        state.adj.add(new TransferImpl(lhs.start));
        state.adj.add(t);
        lhs.to.set(state);
        return new SubGraph(state, t);
    }

    SubGraph atLeastOnce(SubGraph lhs) {
        BaseState state = create(BaseState::new);
        Transfer t = new TransferImpl(null);
        state.adj.add(new TransferImpl(lhs.start));
        state.adj.add(t);
        lhs.to.set(state);
        return new SubGraph(lhs.start, t);
    }

    SubGraph fromChar(int... c) {
        MatchMultiState state = create(MatchMultiState::new);
        state.set = Arrays.stream(c).boxed().collect(Collectors.toSet());
        state.to = new TransferImpl(null);
        return new SubGraph(state, state.to);
    }

    SubGraph anything() {
        MatchAllState state = create(MatchAllState::new);
        state.to = new TransferImpl(null);
        return new SubGraph(state, state.to);
    }

    SubGraph excludeChar(int... c) {
        NotMatchMultiState state = create(NotMatchMultiState::new);
        state.set = Arrays.stream(c).boxed().collect(Collectors.toSet());
        state.to = new TransferImpl(null);
        return new SubGraph(state, state.to);
    }

    void throwException(String s) {
        throw new ParseException("Unexpected token at " + offset + ": " + s);
    }

    void assertEq(int v) {
        if (peek() != v) {
            throwException("should be " + v + " but is " + peek());
        }
    }

    void assertNeq(int v) {
        if (peek() == v) {
            throwException("shouldn't be " + v);
        }
    }

    SubGraph concat(Collection<SubGraph> dq) {
        SubGraph g = null;
        for (SubGraph item : dq) {
            if (g == null) {
                g = item;
                continue;
            }
            g = and(g, item);
        }
        if (g == null) {
            BaseState state = create(BaseState::new);
            Transfer transfer = new TransferImpl(null);
            state.adj.add(transfer);
            g = new SubGraph(state, transfer);
        }
        return g;
    }

    <T> void assertNotEmpty(Collection<T> dq) {
        if (dq.isEmpty()) {
            throwException("invalid state with empty precedent");
        }
    }

    int escape(int x) {
        if (x >= 0 && x < escape.length && escape[x] != -1) {
            return escape[x];
        }
        return x;
    }

    public NfaRE parse(String s) {
        this.pattern = s;
        this.offset = 0;
        SubGraph g = parseSubGraph();
        NfaRE re = new NfaRE();

        State finalState = create(FinalState::new);
        g.to.set(finalState);

        re.start = g.start;
        re.accept = Arrays.asList(finalState);
        re.all = all.toArray(new State[0]);

        return re;
    }

    private int readPlainWord() {
        if (peek() == '\\') {
            read();
            assertNeq(-1);
            return escape(read());
        }
        assertNeq(']');
        return read();
    }

    private SubGraph parseSubGraph() {
        Deque<SubGraph> dq = new LinkedList<>();
        while (peek() != -1) {
            if (peek() == '\\') {
                read();
                assertNeq(-1);
                dq.addLast(fromChar(escape(read())));
                continue;
            }
            if (peek() == '(') {
                read();
                dq.addLast(parseSubGraph());
                assertEq(')');
                read();
                continue;
            }
            if (peek() == ')') {
                return concat(dq);
            }
            if (peek() == '+') {
                read();
                assertNotEmpty(dq);
                dq.addLast(atLeastOnce(dq.removeLast()));
                continue;
            }
            if (peek() == '*') {
                read();
                assertNotEmpty(dq);
                dq.addLast(anytime(dq.removeLast()));
                continue;
            }
            if (peek() == '?') {
                read();
                assertNotEmpty(dq);
                dq.addLast(maybe(dq.removeLast()));
                continue;
            }
            if (peek() == '[') {
                read();
                List<Integer> or = new ArrayList<>();
                boolean exclude = false;
                if (peek() == '^') {
                    exclude = true;
                    read();
                }
                while (peek() != -1) {
                    if (peek() == ']') {
                        break;
                    } else if (peek() == '-') {
                        assertNotEmpty(or);
                        read();
                        int last = readPlainWord();
                        int first = or.remove(or.size() - 1);
                        for (int i = first; i <= last; i++) {
                            or.add(i);
                        }
                    } else {
                        or.add(readPlainWord());
                    }
                }
                assertEq(']');
                read();
                int[] seq = or.stream().mapToInt(Integer::intValue).toArray();
                if (exclude) {
                    dq.addLast(excludeChar(seq));
                } else {
                    dq.addLast(fromChar(seq));
                }
                continue;
            }
            if (peek() == '.') {
                read();
                dq.addLast(anything());
                continue;
            }
            if (peek() == '|') {
                read();
                return or(concat(dq), parseSubGraph());
            }
            //normal
            dq.addLast(fromChar(read()));
        }
        return concat(dq);
    }
}
