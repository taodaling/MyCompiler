package org.dalingtao.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NonTerminal extends BaseSymbol {
    List<Production> productions = new ArrayList<>();
    Set<Symbol> first = new HashSet<>();

    @Override
    public Set<Symbol> first() {
        return first;
    }
}
