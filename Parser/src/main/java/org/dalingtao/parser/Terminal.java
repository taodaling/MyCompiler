package org.dalingtao.parser;

import java.util.Collections;
import java.util.Set;

public class Terminal extends BaseSymbol {
    @Override
    public boolean nullable() {
        return false;
    }

    @Override
    public Set<Symbol> first() {
        return Collections.singleton(this);
    }
}
