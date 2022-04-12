package org.dalingtao.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BaseSymbol implements Symbol {
    String name;
    boolean nullable;
    Set<Symbol> follow = new HashSet<>();
    int id;

    @Override
    public int id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean nullable() {
        return nullable;
    }

    @Override
    public Set<Symbol> first() {
        return Collections.emptySet();
    }

    @Override
    public Set<Symbol> follow() {
        return follow;
    }

    @Override
    public String toString() {
        return name;
    }
}
