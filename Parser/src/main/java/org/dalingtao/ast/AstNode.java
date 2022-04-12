package org.dalingtao.ast;

public interface AstNode {
    Object eval(Context context);

    void make(AstNode... children);
}
