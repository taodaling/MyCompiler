package org.dalingtao.sa.st;

public class Scope implements Cloneable {
    PersistentArray<Identifier> pa;

    public Scope(Scope superScope) {
        if (superScope == null) {
            pa = new PersistentArray<>();
        } else {
            pa = pa.clone();
        }
    }

    public Identifier get(int id) {
        return pa.query(id);
    }

    public void set(int id, Identifier identifier) {
        pa.set(id, identifier);
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
