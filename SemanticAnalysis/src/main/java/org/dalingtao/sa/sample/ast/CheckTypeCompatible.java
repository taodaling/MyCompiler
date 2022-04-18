package org.dalingtao.sa.sample.ast;

import org.dalingtao.sa.st.LiteralValue;
import org.dalingtao.sa.st.SemanticException;
import org.dalingtao.sa.st.TypeDef;
import org.dalingtao.sa.st.VarDef;

public class CheckTypeCompatible implements MinJavaSemantics.Visitor<TypeDef> {
    @Override
    public TypeDef visitINTEGER0(MinJavaSemantics.Visitable visitable) {
        //MinJavaSemantics.Visitor.super.visitINTEGER0(visitable);
        return ((LiteralValue) visitable.item).getType();
    }

    @Override
    public TypeDef visitVAR0(MinJavaSemantics.Visitable visitable) {
        return ((VarDef)visitable.item).getType();
    }

    @Override
    public TypeDef visitBOOLEAN0(MinJavaSemantics.Visitable visitable) {
        return ((LiteralValue) visitable.item).getType();
    }

    @Override
    public TypeDef visitVAL2(MinJavaSemantics.Visitable visitable) {
        return visitable.getVisitable(1).accept(this);
    }

    @Override
    public TypeDef visitTYPE0(MinJavaSemantics.Visitable visitable) {
        TypeDef typeDef = (TypeDef) visitable.item;
        if (!typeDef.isDef()) {
            throw new SemanticException("Type " + typeDef.getName() + " hasn't been defined", visitable);
        }
        return null;
    }

    @Override
    public TypeDef visitEXPR0(MinJavaSemantics.Visitable visitable) {
        return visitable.getVisitable(0).accept(this);
    }

    @Override
    public TypeDef visitEXPR1(MinJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }

    private TypeDef binaryOp(MinJavaSemantics.Visitable a, MinJavaSemantics.Visitable b, MinJavaSemantics.Visitable all) {
        var left = a.accept(this);
        var right = b.accept(this);
        if (left != right) {
            throw new SemanticException("Can't perform binary operation between different types", all);
        }
        return left;
    }

    @Override
    public TypeDef visitSET0(MinJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }
}
