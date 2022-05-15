package org.dalingtao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.IntFunction;


public class Treap implements Cloneable {

    public static final Treap NIL = new Treap();

    static {
        NIL.left = NIL.right = NIL;
        NIL.size = 0;
    }

    public Treap left = NIL;
    public Treap right = NIL;
    public int size = 1;
    public Object key;

    public void visit(Consumer consumer) {
        if (this == NIL) {
            return;
        }
        left.visit(consumer);
        consumer.accept(key);
        right.visit(consumer);
    }

    public static Treap build(IntFunction<Treap> func, int l, int r) {
        if (l > r) {
            return Treap.NIL;
        }
        int mid = (l + r) / 2;
        Treap root = func.apply(mid);
        root.left = build(func, l, mid - 1);
        root.right = build(func, mid + 1, r);
        root.pushUp();
        return root;
    }

    @Override
    public Treap clone() {
        try {
            return (Treap) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void pushDown() {
        if (this == NIL) {
            return;
        }
    }

    public void pushUp() {
        if (this == NIL) {
            return;
        }
        size = left.size + right.size + 1;
    }

    /**
     * split by rank and the node whose rank is argument will stored at result[0]
     */
    public static Treap[] splitByRank(Treap root, int rank) {
        if (root == NIL) {
            return new Treap[]{NIL, NIL};
        }
        root.pushDown();
        Treap[] result;
        if (root.left.size >= rank) {
            result = splitByRank(root.left, rank);
            root.left = result[1];
            result[1] = root;
        } else {
            result = splitByRank(root.right, rank - (root.size - root.right.size));
            root.right = result[0];
            result[0] = root;
        }
        root.pushUp();
        return result;
    }

    public static Treap merge(Treap a, Treap b) {
        if (a == NIL) {
            return b;
        }
        if (b == NIL) {
            return a;
        }
        if (ThreadLocalRandom.current().nextInt(a.size + b.size) < a.size) {
            a.pushDown();
            a.right = merge(a.right, b);
            a.pushUp();
            return a;
        } else {
            b.pushDown();
            b.left = merge(a, b.left);
            b.pushUp();
            return b;
        }
    }

    public static void toString(Treap root, StringBuilder builder) {
        if (root == NIL) {
            return;
        }
        root.pushDown();
        toString(root.left, builder);
        builder.append(root.key).append(',');
        toString(root.right, builder);
    }

    public static Treap clone(Treap root) {
        if (root == NIL) {
            return NIL;
        }
        Treap clone = root.clone();
        clone.left = clone(root.left);
        clone.right = clone(root.right);
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(key).append(":");
        toString(clone(this), builder);
        return builder.toString();
    }

    public Object leftist() {
        Treap node = this;
        while (node.left != null) {
            node = node.left;
        }
        return node.key;
    }

    public List toList() {
        List ans = new ArrayList();
        visit(ans::add);
        return ans;
    }
}
