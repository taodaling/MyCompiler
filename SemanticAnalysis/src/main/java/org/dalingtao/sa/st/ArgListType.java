package org.dalingtao.sa.st;

import org.dalingtao.FastList;

import java.util.ArrayList;
import java.util.List;

public class ArgListType extends TypeDef {
    List<TypeDef> types = new ArrayList<>();

    public List<TypeDef> getTypes() {
        return types;
    }

    public void setTypes(List<TypeDef> types) {
        this.types = types;
    }
}
