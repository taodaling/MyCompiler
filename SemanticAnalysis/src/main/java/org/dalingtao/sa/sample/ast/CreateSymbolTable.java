package org.dalingtao.sa.sample.ast;

import org.dalingtao.FastList;
import org.dalingtao.sa.st.BaseSymbolTableVisitor;
import org.dalingtao.sa.st.FunctionDef;
import org.dalingtao.sa.st.LiteralValue;
import org.dalingtao.sa.st.Scope;
import org.dalingtao.sa.st.SemanticException;
import org.dalingtao.sa.st.TypeDef;
import org.dalingtao.sa.st.VarDef;

import java.util.ArrayList;
import java.util.List;

public class CreateSymbolTable extends BaseSymbolTableVisitor implements MiniJavaSemantics.Visitor<Void> {
    {
        TypeDef intType = getType(null, getId("int"));
        intType.setDef(true);
        TypeDef booleanType = getType(null, getId("boolean"));
        booleanType.setDef(true);
        TypeDef functionType = getType(null, getId("function"));
        functionType.setDef(true);
    }

    @Override
    public Void visitTYPE0(MiniJavaSemantics.Visitable visitable) {
        //using Type
        visitable.item = getType(visitable, getId(visitable.getTerminalBody()));
        return null;
    }

    @Override
    public Void visitVAR_DEF0(MiniJavaSemantics.Visitable visitable) {
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
    public Void visitVAR0(MiniJavaSemantics.Visitable visitable) {
        visitable.item = getVar(visitable, getId(visitable.getTerminalBody()), false);
        return null;
    }

    @Override
    public Void visitVAR_DEF1(MiniJavaSemantics.Visitable visitable) {
        var var = createVar(visitable.getVisitable(1).getVisitable(0), visitable.getVisitable(0));
        visitable.item = var;
        return MiniJavaSemantics.Visitor.super.visitVAR_DEF1(visitable);
    }

    @Override
    public Void visitVAR_DEF2(MiniJavaSemantics.Visitable visitable) {
        var var = createVar(visitable.getVisitable(1), visitable.getVisitable(0));
        var.setType(getArrayType(var.getType()));
        visitable.item = var;
        return MiniJavaSemantics.Visitor.super.visitVAR_DEF2(visitable);
    }

    @Override
    public Void visitSTATEMENT2(MiniJavaSemantics.Visitable visitable) {
        scopeDq.addLast(scopeDq.getLast().clone());
        visitable.getVisitable(1).accept(this);
        scopeDq.removeLast();
        return null;
    }

    @Override
    public Void visitINTEGER0(MiniJavaSemantics.Visitable visitable) {
        visitable.item = new LiteralValue(Integer.parseInt(visitable.getTerminalBody()), getType(null, getId("int")));
        return null;
    }

    @Override
    public Void visitBOOLEAN0(MiniJavaSemantics.Visitable visitable) {
        visitable.item = new LiteralValue(visitable.getTerminalBody().equals("true"), getType(null, getId("boolean")));
        return null;
    }

    @Override
    public Void visitFUNCTION0(MiniJavaSemantics.Visitable visitable) {
        int id = getId(visitable.getTerminalBody());
        visitable.item = getFunction(visitable, id);
        return null;
    }

    @Override
    public Void visitFUNCTION_DEF0(MiniJavaSemantics.Visitable visitable) {
        visitable.getVisitable(0).accept(this);
        visitable.getVisitable(1).accept(this);
        FunctionDef f = (FunctionDef) visitable.getVisitable(1).item;
        scopeDq.addLast(scopeDq.getLast().clone());
        scopeDq.getLast().setFunction(f);
        visitable.getVisitable(3).accept(this);
        visitable.getVisitable(6).accept(this);
        scopeDq.removeLast();

        if (f.isDef()) {
            throw new SemanticException("Duplicate define of function", visitable);
        }
        f.setDef(true);
        f.setRetType((TypeDef) visitable.getVisitable(0).item);
        f.setArgTypes((List<VarDef>) visitable.getVisitable(3).item);
        visitable.item = f;
        return null;
    }

    @Override
    public Void visitARG_DEF_LIST0(MiniJavaSemantics.Visitable visitable) {
        visitable.item = new ArrayList<>();
        return null;
    }

    @Override
    public Void visitARG_DEF_LIST1(MiniJavaSemantics.Visitable visitable) {
        MiniJavaSemantics.Visitor.super.visitARG_DEF_LIST1(visitable);
        List<VarDef> list = (List<VarDef>) visitable.getVisitable(0).item;
        list.add((VarDef) visitable.getVisitable(1).item);
        visitable.item = list;
        return null;
    }

    @Override
    public Void visitSCOPE_BEGIN0(MiniJavaSemantics.Visitable visitable) {
        scopeDq.addLast(scopeDq.getLast().clone());
        return null;
    }

    @Override
    public Void visitSCOPE_END0(MiniJavaSemantics.Visitable visitable) {
        scopeDq.removeLast();
        return null;
    }

    VarDef createVar(MiniJavaSemantics.Visitable var, MiniJavaSemantics.Visitable type) {
        VarDef def = super.getVar(var, getId(var.getTerminalBody()), true);
        def.setType(super.getType(type, getId(type.getTerminalBody())));
        return def;
    }

    @Override
    public Void visitARG_DEF0(MiniJavaSemantics.Visitable visitable) {
        var item = createVar(visitable.getVisitable(0), visitable.getVisitable(2));
        visitable.item = item;
        return MiniJavaSemantics.Visitor.super.visitARG_DEF0(visitable);
    }

    @Override
    public Void visitRETURN0(MiniJavaSemantics.Visitable visitable) {
        MiniJavaSemantics.Visitor.super.visitRETURN0(visitable);
        visitable.item = getCurrentScope();
        return null;
    }
}
