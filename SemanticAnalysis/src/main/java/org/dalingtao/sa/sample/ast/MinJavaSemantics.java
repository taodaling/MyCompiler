package org.dalingtao.sa.sample.ast;
import org.dalingtao.ast.NonTerminalNode;

public abstract class MinJavaSemantics {
    public static abstract class Visitable extends NonTerminalNode {
    public Object item;

    public abstract <T> T accept(Visitor<T> visitor);

    public Visitable getVisitable(int i) {
        return (Visitable) get(i);
    }
}

public static abstract class INTEGER extends Visitable {
}

/**
 * INTEGER -> [int]
 */
public static class INTEGER0 extends INTEGER {
    public INTEGER0 get() {
        return new INTEGER0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitINTEGER0(this);
    }
}

public static abstract class BOOLEAN extends Visitable {
}

/**
 * BOOLEAN -> [boolean]
 */
public static class BOOLEAN0 extends BOOLEAN {
    public BOOLEAN0 get() {
        return new BOOLEAN0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitBOOLEAN0(this);
    }
}

public static abstract class VAL extends Visitable {
}

/**
 * VAL -> [INTEGER]
 */
public static class VAL0 extends VAL {
    public VAL0 get() {
        return new VAL0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL0(this);
    }
}

/**
 * VAL -> [VAR]
 */
public static class VAL1 extends VAL {
    public VAL1 get() {
        return new VAL1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL1(this);
    }
}

/**
 * VAL -> [(, EXPR, )]
 */
public static class VAL2 extends VAL {
    public VAL2 get() {
        return new VAL2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL2(this);
    }
}

/**
 * VAL -> [BOOLEAN]
 */
public static class VAL3 extends VAL {
    public VAL3 get() {
        return new VAL3();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL3(this);
    }
}

public static abstract class TYPE extends Visitable {
}

/**
 * TYPE -> [id]
 */
public static class TYPE0 extends TYPE {
    public TYPE0 get() {
        return new TYPE0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitTYPE0(this);
    }
}

public static abstract class EXPR extends Visitable {
}

/**
 * EXPR -> [VAL]
 */
public static class EXPR0 extends EXPR {
    public EXPR0 get() {
        return new EXPR0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR0(this);
    }
}

/**
 * EXPR -> [EXPR, +, VAL]
 */
public static class EXPR1 extends EXPR {
    public EXPR1 get() {
        return new EXPR1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR1(this);
    }
}

public static abstract class SET extends Visitable {
}

/**
 * SET -> [VAR, eq, EXPR]
 */
public static class SET0 extends SET {
    public SET0 get() {
        return new SET0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSET0(this);
    }
}

public static abstract class VAR extends Visitable {
}

/**
 * VAR -> [id]
 */
public static class VAR0 extends VAR {
    public VAR0 get() {
        return new VAR0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAR0(this);
    }
}

public static abstract class VAR_DEF extends Visitable {
}

/**
 * VAR_DEF -> [TYPE, VAR]
 */
public static class VAR_DEF0 extends VAR_DEF {
    public VAR_DEF0 get() {
        return new VAR_DEF0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAR_DEF0(this);
    }
}

/**
 * VAR_DEF -> [TYPE, SET]
 */
public static class VAR_DEF1 extends VAR_DEF {
    public VAR_DEF1 get() {
        return new VAR_DEF1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAR_DEF1(this);
    }
}

public static abstract class STATEMENT extends Visitable {
}

/**
 * STATEMENT -> [VAR_DEF, ;]
 */
public static class STATEMENT0 extends STATEMENT {
    public STATEMENT0 get() {
        return new STATEMENT0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT0(this);
    }
}

/**
 * STATEMENT -> [SET, ;]
 */
public static class STATEMENT1 extends STATEMENT {
    public STATEMENT1 get() {
        return new STATEMENT1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT1(this);
    }
}

/**
 * STATEMENT -> [{, STATEMENTS, }]
 */
public static class STATEMENT2 extends STATEMENT {
    public STATEMENT2 get() {
        return new STATEMENT2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT2(this);
    }
}

public static abstract class STATEMENTS extends Visitable {
}

/**
 * STATEMENTS -> [STATEMENT, STATEMENTS]
 */
public static class STATEMENTS0 extends STATEMENTS {
    public STATEMENTS0 get() {
        return new STATEMENTS0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENTS0(this);
    }
}

/**
 * STATEMENTS -> []
 */
public static class STATEMENTS1 extends STATEMENTS {
    public STATEMENTS1 get() {
        return new STATEMENTS1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENTS1(this);
    }
}

public static abstract class PROGRAM extends Visitable {
}

/**
 * PROGRAM -> [STATEMENTS]
 */
public static class PROGRAM0 extends PROGRAM {
    public PROGRAM0 get() {
        return new PROGRAM0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitPROGRAM0(this);
    }
}

public static abstract class PROGRAM$ extends Visitable {
}

/**
 * PROGRAM$ -> [PROGRAM, eof]
 */
public static class PROGRAM$0 extends PROGRAM$ {
    public PROGRAM$0 get() {
        return new PROGRAM$0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitPROGRAM$0(this);
    }
}

public interface Visitor<T> {
    /**
 * INTEGER -> [int]
 */
 default T visitINTEGER0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * BOOLEAN -> [boolean]
 */
 default T visitBOOLEAN0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAL -> [INTEGER]
 */
 default T visitVAL0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAL -> [VAR]
 */
 default T visitVAL1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAL -> [(, EXPR, )]
 */
 default T visitVAL2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAL -> [BOOLEAN]
 */
 default T visitVAL3(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * TYPE -> [id]
 */
 default T visitTYPE0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * EXPR -> [VAL]
 */
 default T visitEXPR0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * EXPR -> [EXPR, +, VAL]
 */
 default T visitEXPR1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * SET -> [VAR, eq, EXPR]
 */
 default T visitSET0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAR -> [id]
 */
 default T visitVAR0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAR_DEF -> [TYPE, VAR]
 */
 default T visitVAR_DEF0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * VAR_DEF -> [TYPE, SET]
 */
 default T visitVAR_DEF1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [VAR_DEF, ;]
 */
 default T visitSTATEMENT0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [SET, ;]
 */
 default T visitSTATEMENT1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [{, STATEMENTS, }]
 */
 default T visitSTATEMENT2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * STATEMENTS -> [STATEMENT, STATEMENTS]
 */
 default T visitSTATEMENTS0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * STATEMENTS -> []
 */
 default T visitSTATEMENTS1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * PROGRAM -> [STATEMENTS]
 */
 default T visitPROGRAM0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }
/**
 * PROGRAM$ -> [PROGRAM, eof]
 */
 default T visitPROGRAM$0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            ans = ((Visitable)node).accept(this);
        }
    }
    return ans;
 }

}


}
