package org.dalingtao.sa.st;

import java.util.Map;

public class PersistentArray<T> implements Cloneable {
    PersistentBST<T> root = new PersistentBST<>();

    public void set(int id, T val) {
        root = root.update(id, val);
    }

    public T query(int id) {
        return root.query(id);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public Map<Integer, T> toMap() {
        return root.toMap();
    }

    @Override
    public PersistentArray<T> clone() {
        try {
            return (PersistentArray<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
