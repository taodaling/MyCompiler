package org.dalingtao.sa.st;

import org.dalingtao.ToStringXml;

public class FunctionModule<T> {
    FunctionDef f;
    T item;

    public FunctionModule(FunctionDef f, T item) {
        this.f = f;
        this.item = item;
    }

    public FunctionDef getF() {
        return f;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "<Module>" + ToStringXml.toString("Func", f) +
                ToStringXml.toString("Body", item) + "</Module>";
    }
}
