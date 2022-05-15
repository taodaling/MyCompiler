package org.dalingtao.sa.sample.ast;

import org.dalingtao.sa.st.ArgListType;
import org.dalingtao.sa.st.ArrayTypeDef;
import org.dalingtao.sa.st.FunctionDef;
import org.dalingtao.sa.st.LiteralValue;
import org.dalingtao.sa.st.Scope;
import org.dalingtao.sa.st.SemanticException;
import org.dalingtao.sa.st.TypeDef;
import org.dalingtao.sa.st.VarDef;

import java.util.List;

public class CheckTypeCompatible implements MiniJavaSemantics.Visitor<TypeDef> {
    public CheckTypeCompatible(CreateSymbolTable st) {
        this.st = st;
    }

    CreateSymbolTable st;

    @Override
    public TypeDef visitINTEGER0(MiniJavaSemantics.Visitable visitable) {
        //MinJavaSemantics.Visitor.super.visitINTEGER0(visitable);
        return ((LiteralValue) visitable.item).getType();
    }

    @Override
    public TypeDef visitVAR0(MiniJavaSemantics.Visitable visitable) {
        return ((VarDef) visitable.item).getType();
    }

    @Override
    public TypeDef visitBOOLEAN0(MiniJavaSemantics.Visitable visitable) {
        return ((LiteralValue) visitable.item).getType();
    }

    @Override
    public TypeDef visitVAL2(MiniJavaSemantics.Visitable visitable) {
        return visitable.getVisitable(1).accept(this);
    }

    @Override
    public TypeDef visitTYPE0(MiniJavaSemantics.Visitable visitable) {
        TypeDef typeDef = (TypeDef) visitable.item;
        if (!typeDef.isDef()) {
            throw new SemanticException("Type " + typeDef.getName() + " hasn't been defined", visitable);
        }
        return null;
    }

    @Override
    public TypeDef visitSET0(MiniJavaSemantics.Visitable visitable) {
        var left = visitable.getVisitable(0).accept(this);
        var right = visitable.getVisitable(2).accept(this);
        if ((left instanceof ArrayTypeDef) != (right instanceof ArrayTypeDef)) {
            throw new SemanticException("Can't assign value between incompatible types", visitable);
        }
        return null;
    }

    private TypeDef binaryOp(MiniJavaSemantics.Visitable a, MiniJavaSemantics.Visitable b, MiniJavaSemantics.Visitable all) {
        var left = isScalar(a);
        var right = isScalar(b);
        return left;
    }

    private TypeDef isScalar(MiniJavaSemantics.Visitable a) {
        var type = a.accept(this);
        if (!type.getName().equals("boolean") && !type.getName().equals("int")) {
            throw new SemanticException("Scalar value is required", a);
        }
        return type;
    }

    private ArrayTypeDef isArray(MiniJavaSemantics.Visitable a) {
        var type = a.accept(this);
        if (!(type instanceof ArrayTypeDef)) {
            throw new SemanticException("Array type is required", a);
        }
        return (ArrayTypeDef) type;
    }

    @Override
    public TypeDef visitVAL4(MiniJavaSemantics.Visitable visitable) {
        var type = isArray(visitable.getVisitable(0));
        var indexType = isScalar(visitable.getVisitable(2));
        return type.getRawType();
    }

    @Override
    public TypeDef visitEXPR_MUL1(MiniJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }

    @Override
    public TypeDef visitEXPR_MUL2(MiniJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }

    @Override
    public TypeDef visitEXPR_PLUS1(MiniJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }

    @Override
    public TypeDef visitEXPR_PLUS2(MiniJavaSemantics.Visitable visitable) {
        return binaryOp(visitable.getVisitable(0), visitable.getVisitable(2), visitable);
    }


    @Override
    public TypeDef visitEXPR_AND1(MiniJavaSemantics.Visitable visitable) {
        MiniJavaSemantics.Visitor.super.visitEXPR_AND1(visitable);
        return st.getTypeDef("boolean");
    }


    @Override
    public TypeDef visitEXPR_OR1(MiniJavaSemantics.Visitable visitable) {
        MiniJavaSemantics.Visitor.super.visitEXPR_OR1(visitable);
        return st.getTypeDef("boolean");
    }

    @Override
    public TypeDef visitIF_FLOW0(MiniJavaSemantics.Visitable visitable) {
        isScalar(visitable.getVisitable(2));
        visitable.getVisitable(5).accept(this);
        visitable.getVisitable(7).accept(this);
        return null;
    }

    @Override
    public TypeDef visitWHILE_FLOW0(MiniJavaSemantics.Visitable visitable) {
        isScalar(visitable.getVisitable(2));
        visitable.getVisitable(5).accept(this);
        return null;
    }

    @Override
    public TypeDef visitFUNCTION0(MiniJavaSemantics.Visitable visitable) {
        FunctionDef f = (FunctionDef) visitable.item;
        if (!f.isDef()) {
            throw new SemanticException("Function not define", visitable);
        }
        return st.getTypeDef("function");
    }

    @Override
    public TypeDef visitEXPR_NOT1(MiniJavaSemantics.Visitable visitable) {
        return isScalar(visitable.getVisitable(1));
    }

    @Override
    public TypeDef visitVAL5(MiniJavaSemantics.Visitable visitable) {
        visitable.getVisitable(0).accept(this);
        FunctionDef f = (FunctionDef) visitable.getVisitable(0).item;
        List<TypeDef> args = ((ArgListType) visitable.getVisitable(2).accept(this)).getTypes();
        //check compatible
        if (f.getArgTypes().size() != args.size()) {
            throw new SemanticException("Wrong number of arguments", visitable);
        }
        for (int i = 0; i < f.getArgTypes().size(); i++) {
            VarDef declare = f.getArgTypes().get(i);
            TypeDef type = args.get(i);
            if (declare.getType() != type) {
                throw new SemanticException("Wrong type of arguments", visitable);
            }
        }
        return f.getRetType();
    }

    @Override
    public TypeDef visitRETURN0(MiniJavaSemantics.Visitable visitable) {
        TypeDef res = visitable.getVisitable(1).accept(this);
        Scope scope = (Scope) visitable.item;
        if (scope.getFunction() == null) {
            throw new SemanticException("can't return in global env", visitable);
        }
        if (scope.getFunction().getRetType() != res) {
            throw new SemanticException("incompatible return type", visitable);
        }
        return res;
    }

    @Override
    public TypeDef visitARG_LIST0(MiniJavaSemantics.Visitable visitable) {
        return new ArgListType();
    }

    @Override
    public TypeDef visitARG_LIST1(MiniJavaSemantics.Visitable visitable) {
        ArgListType t = (ArgListType) visitable.getVisitable(0).accept(this);
        t.getTypes().add(visitable.getVisitable(1).accept(this));
        return t;
    }
}
