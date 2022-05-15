package org.dalingtao.sa.st;

import java.util.ArrayList;
import java.util.List;

public class FunctionDef extends Identifier {
    List<VarDef> argTypes = new ArrayList<>();
    TypeDef retType;

    public List<VarDef> getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(List<VarDef> argTypes) {
        this.argTypes = argTypes;
    }

    public TypeDef getRetType() {
        return retType;
    }

    public void setRetType(TypeDef retType) {
        this.retType = retType;
    }
}
