package org.dalingtao.sample;
import org.dalingtao.ast.NonTerminalNode;

public abstract class Factory {
    public static abstract class Visitable extends NonTerminalNode {
    public abstract <T> T visit(Visitor<T> visitor);

    public Visitable getVisitable(int i) {
        return (Visitable) get(i);
    }
}

public static abstract class VAL extends Visitable {
}

/**
 * VAL -> [number]
 */
public static class VAL0 extends VAL {
    public VAL0 get() {
        return new VAL0();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitVAL0(this);
    }
}

/**
 * VAL -> [(, EXPR2, )]
 */
public static class VAL1 extends VAL {
    public VAL1 get() {
        return new VAL1();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitVAL1(this);
    }
}

public static abstract class EXPR1 extends Visitable {
}

/**
 * EXPR1 -> [VAL]
 */
public static class EXPR10 extends EXPR1 {
    public EXPR10 get() {
        return new EXPR10();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR10(this);
    }
}

/**
 * EXPR1 -> [EXPR1, *, VAL]
 */
public static class EXPR11 extends EXPR1 {
    public EXPR11 get() {
        return new EXPR11();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR11(this);
    }
}

/**
 * EXPR1 -> [EXPR1, /, VAL]
 */
public static class EXPR12 extends EXPR1 {
    public EXPR12 get() {
        return new EXPR12();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR12(this);
    }
}

public static abstract class EXPR2 extends Visitable {
}

/**
 * EXPR2 -> [EXPR1]
 */
public static class EXPR20 extends EXPR2 {
    public EXPR20 get() {
        return new EXPR20();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR20(this);
    }
}

/**
 * EXPR2 -> [EXPR2, +, EXPR1]
 */
public static class EXPR21 extends EXPR2 {
    public EXPR21 get() {
        return new EXPR21();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR21(this);
    }
}

/**
 * EXPR2 -> [EXPR2, -, EXPR1]
 */
public static class EXPR22 extends EXPR2 {
    public EXPR22 get() {
        return new EXPR22();
    }
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitEXPR22(this);
    }
}

public static abstract class PROGRAM extends Visitable {
}

/**
 * PROGRAM -> [EXPR2]
 */
public static class PROGRAM0 extends PROGRAM {
    public PROGRAM0 get() {
        return new PROGRAM0();
    }
    public<T> T visit(Visitor<T> visitor) {
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
    public<T> T visit(Visitor<T> visitor) {
        return visitor.visitPROGRAM$0(this);
    }
}

public static abstract class Visitor<T> {
    /**
 * VAL -> [number]
 */
 public T visitVAL0(Visitable visitable) {
 return null;
 }
/**
 * VAL -> [(, EXPR2, )]
 */
 public T visitVAL1(Visitable visitable) {
 return null;
 }
/**
 * EXPR1 -> [VAL]
 */
 public T visitEXPR10(Visitable visitable) {
 return null;
 }
/**
 * EXPR1 -> [EXPR1, *, VAL]
 */
 public T visitEXPR11(Visitable visitable) {
 return null;
 }
/**
 * EXPR1 -> [EXPR1, /, VAL]
 */
 public T visitEXPR12(Visitable visitable) {
 return null;
 }
/**
 * EXPR2 -> [EXPR1]
 */
 public T visitEXPR20(Visitable visitable) {
 return null;
 }
/**
 * EXPR2 -> [EXPR2, +, EXPR1]
 */
 public T visitEXPR21(Visitable visitable) {
 return null;
 }
/**
 * EXPR2 -> [EXPR2, -, EXPR1]
 */
 public T visitEXPR22(Visitable visitable) {
 return null;
 }
/**
 * PROGRAM -> [EXPR2]
 */
 public T visitPROGRAM0(Visitable visitable) {
 return null;
 }
/**
 * PROGRAM$ -> [PROGRAM, eof]
 */
 public T visitPROGRAM$0(Visitable visitable) {
 return null;
 }

}


}
