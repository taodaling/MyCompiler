package org.dalingtao.sa.st;

public class LiteralValue {
    Object value;
    TypeDef type;

    public LiteralValue(Object value, TypeDef type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TypeDef getType() {
        return type;
    }

    public void setType(TypeDef type) {
        this.type = type;
    }
}
