package org.dalingtao.sa.st;

public class Scope implements Cloneable {
    PersistentArray<Identifier> pa;
    FunctionDef function;

    public Scope() {
        pa = new PersistentArray<>();
    }

    public Identifier get(int id) {
        return pa.query(id);
    }

    public void set(int id, Identifier identifier) {
        pa.set(id, identifier);
    }


    public FunctionDef getFunction() {
        return function;
    }

    public void setFunction(FunctionDef function) {
        this.function = function;
    }

    @Override
    public Scope clone() {
        try {
            Scope res = (Scope) super.clone();
            res.pa = pa.clone();
            return res;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
