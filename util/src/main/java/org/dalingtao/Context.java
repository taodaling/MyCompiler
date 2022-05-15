package org.dalingtao;

import java.util.HashMap;
import java.util.Map;

public class Context {
    static ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);

    String code;
    int id;
    Object scope;
    Map<String, Object> scopeMap = new HashMap<>();

    public static Context getInstance() {
        return context.get();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int nextId() {
        return id++;
    }

    public Object getScope() {
        return scope;
    }

    public void setScope(Object scope) {
        this.scope = scope;
    }

    public Map<String, Object> getScopeMap() {
        return scopeMap;
    }

    public void setScopeMap(Map<String, Object> scopeMap) {
        this.scopeMap = scopeMap;
    }
}
