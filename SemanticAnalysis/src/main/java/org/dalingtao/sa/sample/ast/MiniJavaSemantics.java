package org.dalingtao.sa.sample.ast;
import org.dalingtao.ast.NonTerminalNode;

public abstract class MiniJavaSemantics {
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

/**
 * VAL -> [VAL, [, EXPR, ]]
 */
public static class VAL4 extends VAL {
    public VAL4 get() {
        return new VAL4();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL4(this);
    }
}

/**
 * VAL -> [FUNCTION, (, ARG_LIST, )]
 */
public static class VAL5 extends VAL {
    public VAL5 get() {
        return new VAL5();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAL5(this);
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

public static abstract class EXPR_NOT extends Visitable {
}

/**
 * EXPR_NOT -> [VAL]
 */
public static class EXPR_NOT0 extends EXPR_NOT {
    public EXPR_NOT0 get() {
        return new EXPR_NOT0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_NOT0(this);
    }
}

/**
 * EXPR_NOT -> [!, VAL]
 */
public static class EXPR_NOT1 extends EXPR_NOT {
    public EXPR_NOT1 get() {
        return new EXPR_NOT1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_NOT1(this);
    }
}

public static abstract class EXPR_MUL extends Visitable {
}

/**
 * EXPR_MUL -> [EXPR_NOT]
 */
public static class EXPR_MUL0 extends EXPR_MUL {
    public EXPR_MUL0 get() {
        return new EXPR_MUL0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_MUL0(this);
    }
}

/**
 * EXPR_MUL -> [EXPR_MUL, *, VAL]
 */
public static class EXPR_MUL1 extends EXPR_MUL {
    public EXPR_MUL1 get() {
        return new EXPR_MUL1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_MUL1(this);
    }
}

/**
 * EXPR_MUL -> [EXPR_MUL, /, VAL]
 */
public static class EXPR_MUL2 extends EXPR_MUL {
    public EXPR_MUL2 get() {
        return new EXPR_MUL2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_MUL2(this);
    }
}

public static abstract class EXPR_PLUS extends Visitable {
}

/**
 * EXPR_PLUS -> [EXPR_MUL]
 */
public static class EXPR_PLUS0 extends EXPR_PLUS {
    public EXPR_PLUS0 get() {
        return new EXPR_PLUS0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_PLUS0(this);
    }
}

/**
 * EXPR_PLUS -> [EXPR_PLUS, +, EXPR_MUL]
 */
public static class EXPR_PLUS1 extends EXPR_PLUS {
    public EXPR_PLUS1 get() {
        return new EXPR_PLUS1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_PLUS1(this);
    }
}

/**
 * EXPR_PLUS -> [EXPR_PLUS, -, EXPR_MUL]
 */
public static class EXPR_PLUS2 extends EXPR_PLUS {
    public EXPR_PLUS2 get() {
        return new EXPR_PLUS2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_PLUS2(this);
    }
}

public static abstract class EXPR_COMP extends Visitable {
}

/**
 * EXPR_COMP -> [EXPR_PLUS]
 */
public static class EXPR_COMP0 extends EXPR_COMP {
    public EXPR_COMP0 get() {
        return new EXPR_COMP0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP0(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, eq, EXPR_PLUS]
 */
public static class EXPR_COMP1 extends EXPR_COMP {
    public EXPR_COMP1 get() {
        return new EXPR_COMP1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP1(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, neq, EXPR_PLUS]
 */
public static class EXPR_COMP2 extends EXPR_COMP {
    public EXPR_COMP2 get() {
        return new EXPR_COMP2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP2(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, lt, EXPR_PLUS]
 */
public static class EXPR_COMP3 extends EXPR_COMP {
    public EXPR_COMP3 get() {
        return new EXPR_COMP3();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP3(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, gt, EXPR_PLUS]
 */
public static class EXPR_COMP4 extends EXPR_COMP {
    public EXPR_COMP4 get() {
        return new EXPR_COMP4();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP4(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, geq, EXPR_PLUS]
 */
public static class EXPR_COMP5 extends EXPR_COMP {
    public EXPR_COMP5 get() {
        return new EXPR_COMP5();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP5(this);
    }
}

/**
 * EXPR_COMP -> [EXPR_PLUS, leq, EXPR_PLUS]
 */
public static class EXPR_COMP6 extends EXPR_COMP {
    public EXPR_COMP6 get() {
        return new EXPR_COMP6();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_COMP6(this);
    }
}

public static abstract class EXPR_AND extends Visitable {
}

/**
 * EXPR_AND -> [EXPR_COMP]
 */
public static class EXPR_AND0 extends EXPR_AND {
    public EXPR_AND0 get() {
        return new EXPR_AND0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_AND0(this);
    }
}

/**
 * EXPR_AND -> [EXPR_AND, &&, EXPR_COMP]
 */
public static class EXPR_AND1 extends EXPR_AND {
    public EXPR_AND1 get() {
        return new EXPR_AND1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_AND1(this);
    }
}

public static abstract class EXPR_OR extends Visitable {
}

/**
 * EXPR_OR -> [EXPR_AND]
 */
public static class EXPR_OR0 extends EXPR_OR {
    public EXPR_OR0 get() {
        return new EXPR_OR0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_OR0(this);
    }
}

/**
 * EXPR_OR -> [EXPR_OR, ||, EXPR_AND]
 */
public static class EXPR_OR1 extends EXPR_OR {
    public EXPR_OR1 get() {
        return new EXPR_OR1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR_OR1(this);
    }
}

public static abstract class EXPR extends Visitable {
}

/**
 * EXPR -> [EXPR_OR]
 */
public static class EXPR0 extends EXPR {
    public EXPR0 get() {
        return new EXPR0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitEXPR0(this);
    }
}

public static abstract class SET extends Visitable {
}

/**
 * SET -> [VAR, assign, EXPR]
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

/**
 * VAR_DEF -> [TYPE, VAR, [, EXPR, ]]
 */
public static class VAR_DEF2 extends VAR_DEF {
    public VAR_DEF2 get() {
        return new VAR_DEF2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitVAR_DEF2(this);
    }
}

public static abstract class RETURN extends Visitable {
}

/**
 * RETURN -> [return, EXPR]
 */
public static class RETURN0 extends RETURN {
    public RETURN0 get() {
        return new RETURN0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitRETURN0(this);
    }
}

public static abstract class IF_FLOW extends Visitable {
}

/**
 * IF_FLOW -> [if, (, EXPR, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END, ELSE_FLOW]
 */
public static class IF_FLOW0 extends IF_FLOW {
    public IF_FLOW0 get() {
        return new IF_FLOW0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitIF_FLOW0(this);
    }
}

public static abstract class SCOPE_BEGIN extends Visitable {
}

/**
 * SCOPE_BEGIN -> [{]
 */
public static class SCOPE_BEGIN0 extends SCOPE_BEGIN {
    public SCOPE_BEGIN0 get() {
        return new SCOPE_BEGIN0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSCOPE_BEGIN0(this);
    }
}

public static abstract class SCOPE_END extends Visitable {
}

/**
 * SCOPE_END -> [}]
 */
public static class SCOPE_END0 extends SCOPE_END {
    public SCOPE_END0 get() {
        return new SCOPE_END0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSCOPE_END0(this);
    }
}

public static abstract class ELSE_FLOW extends Visitable {
}

/**
 * ELSE_FLOW -> []
 */
public static class ELSE_FLOW0 extends ELSE_FLOW {
    public ELSE_FLOW0 get() {
        return new ELSE_FLOW0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitELSE_FLOW0(this);
    }
}

/**
 * ELSE_FLOW -> [else, SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
public static class ELSE_FLOW1 extends ELSE_FLOW {
    public ELSE_FLOW1 get() {
        return new ELSE_FLOW1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitELSE_FLOW1(this);
    }
}

public static abstract class WHILE_FLOW extends Visitable {
}

/**
 * WHILE_FLOW -> [while, (, EXPR, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
public static class WHILE_FLOW0 extends WHILE_FLOW {
    public WHILE_FLOW0 get() {
        return new WHILE_FLOW0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitWHILE_FLOW0(this);
    }
}

public static abstract class FUNCTION extends Visitable {
}

/**
 * FUNCTION -> [id]
 */
public static class FUNCTION0 extends FUNCTION {
    public FUNCTION0 get() {
        return new FUNCTION0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitFUNCTION0(this);
    }
}

public static abstract class ARG_DEF extends Visitable {
}

/**
 * ARG_DEF -> [VAR, :, TYPE, ,]
 */
public static class ARG_DEF0 extends ARG_DEF {
    public ARG_DEF0 get() {
        return new ARG_DEF0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG_DEF0(this);
    }
}

public static abstract class ARG_DEF_LIST extends Visitable {
}

/**
 * ARG_DEF_LIST -> []
 */
public static class ARG_DEF_LIST0 extends ARG_DEF_LIST {
    public ARG_DEF_LIST0 get() {
        return new ARG_DEF_LIST0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG_DEF_LIST0(this);
    }
}

/**
 * ARG_DEF_LIST -> [ARG_DEF_LIST, ARG_DEF]
 */
public static class ARG_DEF_LIST1 extends ARG_DEF_LIST {
    public ARG_DEF_LIST1 get() {
        return new ARG_DEF_LIST1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG_DEF_LIST1(this);
    }
}

public static abstract class ARG extends Visitable {
}

/**
 * ARG -> [EXPR, ,]
 */
public static class ARG0 extends ARG {
    public ARG0 get() {
        return new ARG0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG0(this);
    }
}

public static abstract class ARG_LIST extends Visitable {
}

/**
 * ARG_LIST -> []
 */
public static class ARG_LIST0 extends ARG_LIST {
    public ARG_LIST0 get() {
        return new ARG_LIST0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG_LIST0(this);
    }
}

/**
 * ARG_LIST -> [ARG_LIST, ARG]
 */
public static class ARG_LIST1 extends ARG_LIST {
    public ARG_LIST1 get() {
        return new ARG_LIST1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitARG_LIST1(this);
    }
}

public static abstract class FUNCTION_DEF extends Visitable {
}

/**
 * FUNCTION_DEF -> [TYPE, FUNCTION, (, ARG_DEF_LIST, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
public static class FUNCTION_DEF0 extends FUNCTION_DEF {
    public FUNCTION_DEF0 get() {
        return new FUNCTION_DEF0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitFUNCTION_DEF0(this);
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
 * STATEMENT -> [SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
public static class STATEMENT2 extends STATEMENT {
    public STATEMENT2 get() {
        return new STATEMENT2();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT2(this);
    }
}

/**
 * STATEMENT -> [IF_FLOW]
 */
public static class STATEMENT3 extends STATEMENT {
    public STATEMENT3 get() {
        return new STATEMENT3();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT3(this);
    }
}

/**
 * STATEMENT -> [WHILE_FLOW]
 */
public static class STATEMENT4 extends STATEMENT {
    public STATEMENT4 get() {
        return new STATEMENT4();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT4(this);
    }
}

/**
 * STATEMENT -> [EXPR, ;]
 */
public static class STATEMENT5 extends STATEMENT {
    public STATEMENT5 get() {
        return new STATEMENT5();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT5(this);
    }
}

/**
 * STATEMENT -> [RETURN, ;]
 */
public static class STATEMENT6 extends STATEMENT {
    public STATEMENT6 get() {
        return new STATEMENT6();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitSTATEMENT6(this);
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
 * PROGRAM -> [FUNCTION_DEF]
 */
public static class PROGRAM0 extends PROGRAM {
    public PROGRAM0 get() {
        return new PROGRAM0();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitPROGRAM0(this);
    }
}

/**
 * PROGRAM -> [PROGRAM, FUNCTION_DEF]
 */
public static class PROGRAM1 extends PROGRAM {
    public PROGRAM1 get() {
        return new PROGRAM1();
    }
    public<T> T accept(Visitor<T> visitor) {
        return visitor.visitPROGRAM1(this);
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * VAL -> [VAL, [, EXPR, ]]
 */
 default T visitVAL4(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * VAL -> [FUNCTION, (, ARG_LIST, )]
 */
 default T visitVAL5(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_NOT -> [VAL]
 */
 default T visitEXPR_NOT0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_NOT -> [!, VAL]
 */
 default T visitEXPR_NOT1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_MUL -> [EXPR_NOT]
 */
 default T visitEXPR_MUL0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_MUL -> [EXPR_MUL, *, VAL]
 */
 default T visitEXPR_MUL1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_MUL -> [EXPR_MUL, /, VAL]
 */
 default T visitEXPR_MUL2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_PLUS -> [EXPR_MUL]
 */
 default T visitEXPR_PLUS0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_PLUS -> [EXPR_PLUS, +, EXPR_MUL]
 */
 default T visitEXPR_PLUS1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_PLUS -> [EXPR_PLUS, -, EXPR_MUL]
 */
 default T visitEXPR_PLUS2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS]
 */
 default T visitEXPR_COMP0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, eq, EXPR_PLUS]
 */
 default T visitEXPR_COMP1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, neq, EXPR_PLUS]
 */
 default T visitEXPR_COMP2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, lt, EXPR_PLUS]
 */
 default T visitEXPR_COMP3(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, gt, EXPR_PLUS]
 */
 default T visitEXPR_COMP4(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, geq, EXPR_PLUS]
 */
 default T visitEXPR_COMP5(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_COMP -> [EXPR_PLUS, leq, EXPR_PLUS]
 */
 default T visitEXPR_COMP6(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_AND -> [EXPR_COMP]
 */
 default T visitEXPR_AND0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_AND -> [EXPR_AND, &&, EXPR_COMP]
 */
 default T visitEXPR_AND1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_OR -> [EXPR_AND]
 */
 default T visitEXPR_OR0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR_OR -> [EXPR_OR, ||, EXPR_AND]
 */
 default T visitEXPR_OR1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * EXPR -> [EXPR_OR]
 */
 default T visitEXPR0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * SET -> [VAR, assign, EXPR]
 */
 default T visitSET0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * VAR_DEF -> [TYPE, VAR, [, EXPR, ]]
 */
 default T visitVAR_DEF2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * RETURN -> [return, EXPR]
 */
 default T visitRETURN0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * IF_FLOW -> [if, (, EXPR, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END, ELSE_FLOW]
 */
 default T visitIF_FLOW0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * SCOPE_BEGIN -> [{]
 */
 default T visitSCOPE_BEGIN0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * SCOPE_END -> [}]
 */
 default T visitSCOPE_END0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ELSE_FLOW -> []
 */
 default T visitELSE_FLOW0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ELSE_FLOW -> [else, SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
 default T visitELSE_FLOW1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * WHILE_FLOW -> [while, (, EXPR, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
 default T visitWHILE_FLOW0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * FUNCTION -> [id]
 */
 default T visitFUNCTION0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG_DEF -> [VAR, :, TYPE, ,]
 */
 default T visitARG_DEF0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG_DEF_LIST -> []
 */
 default T visitARG_DEF_LIST0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG_DEF_LIST -> [ARG_DEF_LIST, ARG_DEF]
 */
 default T visitARG_DEF_LIST1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG -> [EXPR, ,]
 */
 default T visitARG0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG_LIST -> []
 */
 default T visitARG_LIST0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * ARG_LIST -> [ARG_LIST, ARG]
 */
 default T visitARG_LIST1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * FUNCTION_DEF -> [TYPE, FUNCTION, (, ARG_DEF_LIST, ), SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
 default T visitFUNCTION_DEF0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [SCOPE_BEGIN, STATEMENTS, SCOPE_END]
 */
 default T visitSTATEMENT2(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [IF_FLOW]
 */
 default T visitSTATEMENT3(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [WHILE_FLOW]
 */
 default T visitSTATEMENT4(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [EXPR, ;]
 */
 default T visitSTATEMENT5(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * STATEMENT -> [RETURN, ;]
 */
 default T visitSTATEMENT6(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * PROGRAM -> [FUNCTION_DEF]
 */
 default T visitPROGRAM0(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }
/**
 * PROGRAM -> [PROGRAM, FUNCTION_DEF]
 */
 default T visitPROGRAM1(Visitable visitable) {
    T ans = null;
    for(var node : visitable) {
        if(node instanceof Visitable) {
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
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
            var res = ((Visitable)node).accept(this);
            if(ans == null) {
                ans = res;
            }
        }
    }
    return ans;
 }

}


}
