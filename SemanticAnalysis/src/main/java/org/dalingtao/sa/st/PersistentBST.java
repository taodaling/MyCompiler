package org.dalingtao.sa.st;

import java.util.Map;
import java.util.TreeMap;

public class PersistentBST<T> implements Cloneable {
    static final PersistentBST NIL = new PersistentBST();
    static final int H = 30;

    static {
        NIL.left = NIL.right = NIL;
    }

    PersistentBST<T> left;
    PersistentBST<T> right;
    T value;

    public PersistentBST() {
        left = right = NIL;
    }

    public PersistentBST<T> update(int id, T value) {
        return update(id, H, value);
    }


    public T query(int id) {
        return query(id, H);
    }

    private PersistentBST<T> update(int id, int h, T value) {
        PersistentBST<T> node = clone();
        if (h < 0) {
            node.value = value;
            return node;
        }
        if (((id >> h) & 1) == 0) {
            node.left = node.left.update(id, h - 1, value);
        } else {
            node.right = node.right.update(id, h - 1, value);
        }
        return node;
    }

    private T query(int id, int h) {
        if (h < 0) {
            return value;
        }
        if (((id >> h) & 1) == 0) {
            return left.query(id, h - 1);
        } else {
            return right.query(id, h - 1);
        }
    }

    private void dfs(int id, int h, Map<Integer, T> data) {
        if (this == NIL) {
            return;
        }
        if (h < 0) {
            data.put(id, value);
            return;
        }
        left.dfs(id | (0 << h), h - 1, data);
        right.dfs(id | (1 << h), h - 1, data);
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    public Map<Integer, T> toMap() {
        TreeMap<Integer, T> map = new TreeMap<>();
        dfs(0, H, map);
        return map;
    }

    @Override
    public PersistentBST<T> clone() {
        try {
            return (PersistentBST) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
