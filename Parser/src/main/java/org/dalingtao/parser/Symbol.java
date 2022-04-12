package org.dalingtao.parser;

import java.util.Set;

public interface Symbol {
    int id();

    String name();

    boolean nullable();

    Set<Symbol> first();

    Set<Symbol> follow();
}
