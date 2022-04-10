package org.dalingtao.re;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class LexerDumper {
    public static void main(String[] args) {
        String[] tokens = new String[]{
                "NUM",
                "OP",
                "LEFT_BRACKET",
                "RIGHT_BRACKET"
        };
        String[] patterns = new String[]{
                "0|[1-9][0-9]*",
                "[+-*/]",
                "\\(",
                "\\)"
        };
        List<NfaRE> nfa = new ArrayList<>();
        for(int i = 0; i < tokens.length; i++) {
            nfa.add(new REParser().parse(patterns[i]));
            int finalI = i;
            nfa.get(nfa.size() - 1).accept.stream().forEach(x -> x.getProperties().put("TOKEN", tokens[finalI]));
            nfa.get(nfa.size() - 1).accept.stream().forEach(x -> x.getProperties().put("IGNORE", false));
        }

        DfaRE dfa = NfaRE.merge(nfa.toArray(new NfaRE[0])).toDfa(128);
        dump(dfa, System.out);
    }

    public static void dump(DfaRE re, OutputStream os) {
        int n = re.mapping.size();
        int charset = re.charset;
        PrintStream ps = new PrintStream(os);
        ps.print(n);
        ps.print(" ");
        ps.print(charset);
        ps.println();
        for (int i = 0; i < n; i++) {
            BitSet state = re.sets.get(i);
            String token = null;
            boolean ignore = false;
            for (int j = 0; j < re.states.length && token == null; j++) {
                if (state.get(j) && re.states[j] instanceof FinalState) {
                    token = (String) re.states[j].getProperties().get("TOKEN");
                    ignore = (Boolean) re.states[j].getProperties().get("IGNORE");
                }
            }
            if (token == null) {
                ps.print(0);
                ps.print(" ");
            } else {
                ps.print(1);
                ps.print(" ");
                ps.print(token);
                ps.print(" ");
                ps.print(ignore ? "0" : "1");
                ps.print(" ");
            }
            for (int j = 0; j < charset; j++) {
                ps.print(re.adj.get(i)[j]);
                ps.print(" ");
            }
            ps.println();
        }
        ps.flush();
    }
}
