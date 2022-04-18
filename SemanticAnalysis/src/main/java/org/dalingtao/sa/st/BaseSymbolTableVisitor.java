package org.dalingtao.sa.st;


import org.dalingtao.ast.AstNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BaseSymbolTableVisitor {
    protected Deque<Scope> scopeDq = new LinkedList<>();

    {
        scopeDq.addLast(new Scope(null));
    }

    protected Map<String, Integer> idMapping = new HashMap<>();
    protected List<String> ids = new ArrayList<>();

    protected int getId(String name) {
        Integer ans = idMapping.get(name);
        if (ans == null) {
            ans = idMapping.size();
            idMapping.put(name, ans);
            ids.add(name);
        }
        return ans;
    }

    protected Scope getCurrentScope() {
        return scopeDq.getLast();
    }

    protected TypeDef getType(AstNode visitable, int id) {
        var obj = scopeDq.getLast().get(id);
        if (obj != null && !(obj instanceof TypeDef)) {
            throw new SemanticException(ids.get(id) + " isn't a type", visitable);
        }
        TypeDef type = (TypeDef) obj;
        var scope = getCurrentScope();
        if (type == null) {
            //make type
            type = new TypeDef();
            type.id = id;
            type.name = ids.get(id);
            type.scope = scope;
            scope.set(id, type);
        }
        return type;
    }

    protected VarDef getVar(AstNode visitable, int id, boolean create) {
        var obj = scopeDq.getLast().get(id);
        if (obj != null && !(obj instanceof VarDef)) {
            throw new SemanticException(ids.get(id) + " isn't a type", visitable);
        }
        VarDef var = (VarDef) obj;
        var scope = getCurrentScope();
        if (var == null || create) {
            if (var == null && !create) {
                throw new SemanticException("variable " + ids.get(id) + " hasn't been defined", visitable);
            }
            //make type
            var = new VarDef();
            var.id = id;
            var.name = ids.get(id);
            var.scope = scope;
            var.def = true;
            scope.set(id, var);
        }
        return var;
    }
}
