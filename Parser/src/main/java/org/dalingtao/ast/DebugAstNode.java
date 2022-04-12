package org.dalingtao.ast;


public class DebugAstNode extends BaseAstNode<DebugAstNode> {

    @Override
    public DebugAstNode get() {
        return new DebugAstNode();
    }
}
