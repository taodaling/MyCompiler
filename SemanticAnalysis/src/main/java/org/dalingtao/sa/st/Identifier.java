package org.dalingtao.sa.st;

import org.dalingtao.Context;

public class Identifier {
    protected int seqId = Context.getInstance().nextId();

    protected String name;
    protected int id;
    protected boolean def;
    protected Scope scope;

    public int getSeqId() {
        return seqId;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return name;
    }
}
