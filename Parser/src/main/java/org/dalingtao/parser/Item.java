package org.dalingtao.parser;

public class Item implements Comparable<Item> {
    Production p;
    Symbol next;
    int offset;
    long val;
    int id;

    @Override
    public boolean equals(Object o) {
        return val == ((Item) o).val;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(val);
    }

    Symbol nextSymbol() {
        if (offset < p.right.size()) {
            return p.right.get(offset);
        }
        return null;
    }

    @Override
    public int compareTo(Item o) {
        return Long.compare(val, o.val);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder(p.left.name).append(" = ");
        ans.append("(");
        for (int i = 0; i < offset; i++) {
            ans.append(p.right.get(i).name()).append(" ");
        }
        ans.append(". ");
        for (int i = offset; i < p.right.size(); i++) {
            ans.append(p.right.get(i).name()).append(" ");
        }
        ans.setLength(ans.length() - 1);
        ans.append(", ").append(next.name()).append(")");
        return ans.toString();
    }
}
