package org.dalingtao;

import java.util.List;

public class FastList<T> {
    Treap treap = Treap.NIL;

    public static <T> FastList<T> singleton(T item) {
        FastList list = new FastList();
        list.treap = new Treap();
        list.treap.key = item;
        return list;
    }

    public FastList<T> addAll(FastList<T> rhs) {
        treap = Treap.merge(treap, rhs.treap);
        return this;
    }

    public FastList<T> add(T item) {
        return addAll(singleton(item));
    }

    public T get(int index) {
        Treap[] pair = Treap.splitByRank(treap, index);
        T ans = (T) pair[1].leftist();
        treap = Treap.merge(pair[0], pair[1]);
        return ans;
    }

    public FastList<T> erase(int index) {
        Treap[] pair = Treap.splitByRank(treap, index);
        pair[1] = Treap.splitByRank(treap, 1)[1];
        treap = Treap.merge(pair[0], pair[1]);
        return this;
    }

    public FastList<T> insert(int index, T val) {
        Treap[] pair = Treap.splitByRank(treap, index);
        pair[1] = Treap.merge(singleton(val).treap, pair[1]);
        treap = Treap.merge(pair[0], pair[1]);
        return this;
    }

    public List<T> toList() {
        return treap.toList();
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    public int size() {
        return treap.size;
    }

}
