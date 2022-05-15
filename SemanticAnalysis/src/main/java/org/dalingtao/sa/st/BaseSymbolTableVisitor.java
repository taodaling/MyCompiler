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
        scopeDq.addLast(new Scope());
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

    public TypeDef getTypeDef(String name) {
        return (TypeDef) getCurrentScope().get(idMapping.get(name));
    }

    protected Scope getCurrentScope() {
        return scopeDq.getLast();
    }

    protected TypeDef getType(AstNode visitable, int id, Scope scope) {
        var obj = getCurrentScope().get(id);
        if (obj != null && !(obj instanceof TypeDef)) {
            throw new SemanticException(ids.get(id) + " isn't a type", visitable);
        }
        TypeDef type = (TypeDef) obj;
        if (type == null) {
            //make type\
            type = new TypeDef();
            init(type, id, scope);
        }
        return type;
    }

    void init(Identifier identifier, int id, Scope scope) {
        identifier.id = id;
        identifier.name = ids.get(id);
        identifier.scope = getCurrentScope();
        scope.set(id, identifier);
    }

    protected FunctionDef getFunction(AstNode visitable, int id) {
        var obj = getCurrentScope().get(id);
        if (obj != null && !(obj instanceof FunctionDef)) {
            throw new SemanticException(ids.get(id) + " isn't a function", visitable);
        }
        FunctionDef function = (FunctionDef) obj;
        if (function == null) {
            function = new FunctionDef();
            init(function, id, getCurrentScope());
        }
        return function;
    }

    protected TypeDef getType(AstNode visitable, int id) {
        return getType(visitable, id, getCurrentScope());
    }

    protected ArrayTypeDef getArrayType(TypeDef raw) {
        if (raw.getArrayType() == null) {
            String name = "[" + raw.getName() + "]";
            int id = getId(name);
            ArrayTypeDef type = new ArrayTypeDef();
            type.setDef(true);
            type.setRawType(raw);
            type.setName(name);
            type.setId(id);
            type.setScope(raw.getScope());
            raw.setArrayType(type);

            type.getScope().set(type.getId(), type);
        }
        return raw.getArrayType();
    }

    protected String typeNameOfArray(String rawType) {
        return "[" + rawType + "]";
    }

    protected VarDef getVar(AstNode visitable, int id, boolean create) {
        return getVar(visitable, id, create, getCurrentScope());
    }

    protected VarDef getVar(AstNode visitable, int id, boolean create, Scope scope) {
        var obj = scopeDq.getLast().get(id);
        if (obj != null && !(obj instanceof VarDef)) {
            throw new SemanticException(ids.get(id) + " isn't a type", visitable);
        }
        VarDef var = (VarDef) obj;
        if (var == null || create) {
            if (var == null && !create) {
                throw new SemanticException("variable " + ids.get(id) + " hasn't been defined", visitable);
            } else if (var != null && create) {
                throw new SemanticException("variable " + ids.get(id) + " has been defined", visitable);
            }
            //make type
            var = new VarDef();
            init(var, id, getCurrentScope());
            var.def = true;
        }
        return var;
    }
}
