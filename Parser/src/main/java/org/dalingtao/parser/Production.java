package org.dalingtao.parser;

import java.util.ArrayList;
import java.util.List;

public class Production {
    int id;
    NonTerminal left;
    List<Symbol> right = new ArrayList<>();


    @Override
    public String toString() {
        return left + " -> " + right.toString();
    }
}
