package org.dalingtao.ast;

import java.util.function.Supplier;

public abstract class NonTerminalNode extends AstNode implements Supplier<NonTerminalNode> {
}
