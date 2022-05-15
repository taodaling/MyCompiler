package org.dalingtao;

public class DSU {
    int[] fa;
    int[] size;

    public DSU(int n) {
        fa = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
            size[i] = 1;
        }
    }

    public int[] size() {
        return size;
    }

    public int find(int i) {
        if (fa[fa[i]] == fa[i]) {
            return fa[i];
        }
        return fa[i] = find(fa[i]);
    }

    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) {
            return;
        }
        if (size[a] < size[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        size[a] += size[b];
        fa[b] = a;
    }
}
