package org.dalingtao.re;

public class DfaMatcher implements Matcher {
    DfaRE re;
    int cur;

    DfaMatcher(DfaRE re) {
        this.re = re;
        cur = 1;
    }

    @Override
    public void consume(int c) {
        cur = re.adj.get(cur)[c];
    }

    @Override
    public boolean match() {
        return re.accept[cur];
    }

    public boolean fail() {
        return cur == 0;
    }
}
