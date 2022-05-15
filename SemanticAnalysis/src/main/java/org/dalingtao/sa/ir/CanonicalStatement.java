package org.dalingtao.sa.ir;

import org.dalingtao.FastList;
import org.dalingtao.ToStringXml;

import java.util.List;

public class CanonicalStatement extends CanonicalInstruction {
    FastList<Statement> s = new FastList<>();

    public CanonicalStatement(Statement... statements) {
        for (Statement statement : statements) {
            s.add(statement);
        }
    }

    public CanonicalStatement add(CanonicalStatement rhs) {
        s.addAll(rhs.s);
        return this;
    }

    public CanonicalStatement add(Statement ss) {
        s.add(ss);
        return this;
    }

    @Override
    public String toString() {
        return ToStringXml.toString("CanonicalStatement", s.toList());
    }

    public int size() {
        return s.size();
    }

    public Statement get(int i) {
        return s.get(i);
    }

    public List<Statement> toList() {
        return s.toList();
    }
}
