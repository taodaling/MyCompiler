package org.dalingtao.ast;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseAstNode<T extends BaseAstNode<T>> implements AstNode, Supplier<T> {
    AstNode[] nodes;

    @Override
    public Object eval(Context context) {
        return null;
    }

    @Override
    public void make(AstNode... children) {
        nodes = children;
    }

    public String toString() {
        return "<" + getClass().getSimpleName() + ">" + Arrays.stream(nodes).map(Objects::toString).collect(Collectors.joining("\n")) + "</" + getClass().getSimpleName() + ">";
    }
}
