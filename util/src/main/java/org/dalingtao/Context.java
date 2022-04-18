package org.dalingtao;

public class Context {
    static ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);

    String code;

    public static Context getInstance() {
        return context.get();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
