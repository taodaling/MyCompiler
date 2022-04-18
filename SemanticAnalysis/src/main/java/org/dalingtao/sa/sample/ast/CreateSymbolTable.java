package org.dalingtao.sa.sample.ast;

import org.dalingtao.sa.st.BaseSymbolTableVisitor;
import org.dalingtao.sa.st.LiteralValue;
import org.dalingtao.sa.st.TypeDef;

public class CreateSymbolTable extends BaseSymbolTableVisitor implements MinJavaSemantics.Visitor<Void> {
    {
        TypeDef intType = getType(null, getId("int"));
        intType.setDef(true);
        TypeDef booleanType = getType(null, getId("boolean"));
        booleanType.setDef(true);
    }

    @Override
    public Void visitTYPE0(MinJavaSemantics.Visitable visitable) {
        //using Type
        String typeName = visitable.get(0).getTerminalBody();
        int id = getId(typeName);
        var type = getType(visitable, id);
        visitable.item = type;
        return null;
    }

    @Override
    public Void visitVAR_DEF0(MinJavaSemantics.Visitable visitable) {
        visitable.getVisitable(0).accept(this);
        String varName = visitable.get(1).getTerminalBody();
        int id = getId(varName);
        TypeDef type = (TypeDef) visitable.getVisitable(0).item;
        var var = getVar(visitable, id, true);
        var.setType(type);
        visitable.getVisitable(1).item = var;
        return null;
    }

    @Override
    public Void visitVAR0(MinJavaSemantics.Visitable visitable) {
        visitable.item = getVar(visitable, getId(visitable.getTerminalBody()), false);
        return null;
    }

    @Override
    public Void visitVAR_DEF1(MinJavaSemantics.Visitable visitable) {
        visitable.getVisitable(0).accept(this);
        String typeName = visitable.get(1).getTerminalBody();
        int id = getId(typeName);
        var type = (TypeDef) visitable.getVisitable(0).item;
        var var = getVar(visitable, id, true);
        var.setType(type);
        visitable.getVisitable(1).accept(this);
        return null;
    }

    @Override
    public Void visitSTATEMENT2(MinJavaSemantics.Visitable visitable) {
        scopeDq.addLast(scopeDq.getLast().clone());
        visitable.getVisitable(1).accept(this);
        scopeDq.removeLast();
        return null;
    }

    @Override
    public Void visitINTEGER0(MinJavaSemantics.Visitable visitable) {
        visitable.item = new LiteralValue(Integer.parseInt(visitable.getTerminalBody()), getType(null, getId("int")));
        return null;
    }

    @Override
    public Void visitBOOLEAN0(MinJavaSemantics.Visitable visitable) {
        visitable.item = new LiteralValue(visitable.getTerminalBody().equals("true"), getType(null, getId("boolean")));
        return null;
    }
}
