package org.dalingtao.ast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

public class AstNode implements Iterable<AstNode> {
    public static final AstNode[] NIL = new AstNode[0];
    protected AstNode[] nodes = NIL;
    int begin;
    int end;

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public void make(AstNode... children) {
        nodes = children;
        if (children.length > 0) {
            begin = children[0].begin;
            end = children[children.length - 1].end;
        }
    }

    public AstNode get(int i) {
        return nodes[i];
    }

    public String getTerminalBody() {
        return get(0).getTerminalBody();
    }

    public int size() {
        return nodes.length;
    }

    @Override
    public Iterator<AstNode> iterator() {
        return Arrays.asList(nodes).iterator();
    }

    public String toString() {
        return "<" + getClass().getSimpleName() + ">" + Arrays.stream(nodes).map(Objects::toString).collect(Collectors.joining("\n")) + "</" + getClass().getSimpleName() + ">";
    }
}
