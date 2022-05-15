package org.dalingtao.sa.sample.ast;

import org.dalingtao.Context;
import org.dalingtao.sa.ir.BinOp;
import org.dalingtao.sa.ir.Call;
import org.dalingtao.sa.ir.Cjump;
import org.dalingtao.sa.ir.Const;
import org.dalingtao.sa.ir.Exp;
import org.dalingtao.sa.ir.Expression;
import org.dalingtao.sa.ir.ExpressionList;
import org.dalingtao.sa.ir.Instruction;
import org.dalingtao.sa.ir.Jump;
import org.dalingtao.sa.ir.Label;
import org.dalingtao.sa.ir.Mem;
import org.dalingtao.sa.ir.Move;
import org.dalingtao.sa.ir.Name;
import org.dalingtao.sa.ir.Nil;
import org.dalingtao.sa.ir.Return;
import org.dalingtao.sa.ir.Seq;
import org.dalingtao.sa.ir.Statement;
import org.dalingtao.sa.ir.UnaryOp;
import org.dalingtao.sa.st.FunctionDef;
import org.dalingtao.sa.st.FunctionModule;
import org.dalingtao.sa.st.Scope;
import org.dalingtao.sa.st.VarDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateIR implements MiniJavaSemantics.Visitor<Instruction> {
    List<FunctionModule<Statement>> functions = new ArrayList<>();

    @Override
    public Instruction visitINTEGER0(MiniJavaSemantics.Visitable visitable) {
        return new Const(Integer.parseInt(visitable.getTerminalBody()));
    }

    @Override
    public Instruction visitBOOLEAN0(MiniJavaSemantics.Visitable visitable) {
        return new Const(visitable.getTerminalBody().equals("true") ? 1 : 0);
    }


    @Override
    public Instruction visitVAL2(MiniJavaSemantics.Visitable visitable) {
        return visitable.getVisitable(1).accept(this);
    }

    @Override
    public Instruction visitVAL4(MiniJavaSemantics.Visitable visitable) {
        var val = (Expression) visitable.getVisitable(0).accept(this);
        var expr = (Expression) visitable.getVisitable(2).accept(this);
        return new Mem(new BinOp.Add(val, new BinOp.Mul(new Const(4), expr)));
    }

    @Override
    public Instruction visitEXPR_MUL1(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Mul(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_MUL2(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Div(lhs, rhs);
    }


    @Override
    public Instruction visitEXPR_PLUS1(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Add(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_PLUS2(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Sub(lhs, rhs);
    }


    @Override
    public Instruction visitEXPR_COMP1(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Eq(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_COMP2(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Neq(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_COMP3(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Lt(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_COMP4(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Gt(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_COMP5(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Geq(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_COMP6(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.Leq(lhs, rhs);
    }


    @Override
    public Instruction visitEXPR_AND1(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.LogicAnd(lhs, rhs);
    }

    @Override
    public Instruction visitEXPR_OR1(MiniJavaSemantics.Visitable visitable) {
        var lhs = (Expression) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new BinOp.LogicOr(lhs, rhs);
    }

    @Override
    public Instruction visitSET0(MiniJavaSemantics.Visitable visitable) {
        var lhs = (org.dalingtao.sa.ir.Temp) visitable.getVisitable(0).accept(this);
        var rhs = (Expression) visitable.getVisitable(2).accept(this);
        return new Move.Temp(lhs, rhs);
    }

    @Override
    public Instruction visitVAR0(MiniJavaSemantics.Visitable visitable) {
        return new org.dalingtao.sa.ir.Temp(((VarDef) visitable.item).getSeqId());
    }

    @Override
    public Instruction visitVAR_DEF0(MiniJavaSemantics.Visitable visitable) {
        var lhs = (org.dalingtao.sa.ir.Temp) visitable.getVisitable(1).accept(this);
        return new Move.Temp(lhs, new Const(0));
    }

    @Override
    public Instruction visitVAR_DEF1(MiniJavaSemantics.Visitable visitable) {
        return visitable.getVisitable(1).accept(this);
    }

    @Override
    public Instruction visitVAR_DEF2(MiniJavaSemantics.Visitable visitable) {
        var lhs = (org.dalingtao.sa.ir.Temp) visitable.getVisitable(1).accept(this);
        var expr = (Expression) visitable.getVisitable(3).accept(this);
        return new Move.Temp(
                lhs,
                new Call(
                        new Name("malloc"),
                        new BinOp.Mul(expr, new Const(4))
                )
        );
    }


    int nextLabel() {
        return Context.getInstance().nextId();
    }

    @Override
    public Instruction visitIF_FLOW0(MiniJavaSemantics.Visitable visitable) {
        var cond = (Expression) visitable.getVisitable(2).accept(this);
        var t = (Statement) visitable.getVisitable(5).accept(this);
        var f = (Statement) visitable.getVisitable(7).accept(this);
        var tLabel = new Label(nextLabel());
        var fLabel = new Label(nextLabel());
        var endLabel = new Label(nextLabel());
        return new Seq(
                rewriteControl(
                        cond,
                        tLabel,
                        fLabel
                ),
                tLabel,
                t,
                new Jump(endLabel.toName()),
                fLabel,
                f,
                endLabel
        );
    }

    @Override
    public Instruction visitELSE_FLOW0(MiniJavaSemantics.Visitable visitable) {
        return new Nil();
    }

    @Override
    public Instruction visitELSE_FLOW1(MiniJavaSemantics.Visitable visitable) {
        var stat = (Statement) visitable.getVisitable(2).accept(this);
        return stat;
    }

    @Override
    public Instruction visitWHILE_FLOW0(MiniJavaSemantics.Visitable visitable) {
        var cond = (Expression) visitable.getVisitable(2).accept(this);
        var t = (Statement) visitable.getVisitable(5).accept(this);
        var goback = new Label(nextLabel());
        var tLabel = new Label(nextLabel());
        var endLabel = new Label(nextLabel());
        return new Seq(goback, rewriteControl(cond, tLabel, endLabel),
                tLabel, t, new Jump(goback.toName()), endLabel);
    }

    @Override
    public Instruction visitSTATEMENTS0(MiniJavaSemantics.Visitable visitable) {
        var e1 = (Statement) visitable.getVisitable(0).accept(this);
        var e2 = (Statement) visitable.getVisitable(1).accept(this);
        return new Seq(e1, e2);
    }

    Statement rewriteControl(Expression cond, Label t, Label f) {
        if (cond instanceof Const) {
            Const cast = (Const) cond;
            if (cast.getVal().equals("0")) {
                return new Jump(t.toName());
            } else {
                return new Jump(f.toName());
            }
        }
        if (cond instanceof BinOp.LogicOr) {
            BinOp.LogicOr cast = (BinOp.LogicOr) cond;
            var a = cast.getA();
            var b = cast.getB();
            Label newLabel = new Label(nextLabel());
            return new Seq(rewriteControl(a, t, newLabel),
                    newLabel,
                    rewriteControl(b, t, f));
        }

        if (cond instanceof BinOp.LogicAnd) {
            BinOp.LogicAnd cast = (BinOp.LogicAnd) cond;
            var a = cast.getA();
            var b = cast.getB();
            Label newLabel = new Label(nextLabel());
            return new Seq(rewriteControl(a, newLabel, f),
                    newLabel,
                    rewriteControl(b, t, f));
        }

        return new Cjump(cond, t.toName(), f.toName());
    }

    @Override
    public Instruction visitSTATEMENTS1(MiniJavaSemantics.Visitable visitable) {
        return new Nil();
    }

    @Override
    public Instruction visitEXPR_NOT1(MiniJavaSemantics.Visitable visitable) {
        return new UnaryOp.LogicNot((Expression) visitable.getVisitable(1).accept(this));
    }

    @Override
    public Instruction visitSTATEMENT5(MiniJavaSemantics.Visitable visitable) {
        return new Exp((Expression) visitable.getVisitable(0).accept(this));
    }

    @Override
    public Instruction visitFUNCTION0(MiniJavaSemantics.Visitable visitable) {
        return new Label(visitable.getTerminalBody());
    }

    @Override
    public Instruction visitVAL5(MiniJavaSemantics.Visitable visitable) {
        Label label = (Label) visitable.getVisitable(0).accept(this);
        ExpressionList list = (ExpressionList) visitable.getVisitable(2).accept(this);
        return new Call(label.toName(), list.getList().toArray(new Expression[0]));
    }

    void saveFunction(FunctionDef f, Statement s) {
        FunctionModule ir = new FunctionModule(f, s);
        functions.add(ir);
    }

    @Override
    public Instruction visitFUNCTION_DEF0(MiniJavaSemantics.Visitable visitable) {
        //Label label = (Label) visitable.getVisitable(1).accept(this);
        Statement statement = (Statement) visitable.getVisitable(6).accept(this);
        //var res = new Seq(label, statement);
        FunctionDef def = (FunctionDef) visitable.getVisitable(1).item;
        saveFunction(def, statement);
        return null;
    }


    @Override
    public Instruction visitRETURN0(MiniJavaSemantics.Visitable visitable) {
        Expression e = (Expression) visitable.getVisitable(1).accept(this);
        Scope scope = (Scope) visitable.item;
        return new Return.ReturnVal(scope.getFunction().getName(), e);
    }

    @Override
    public Instruction visitARG_LIST0(MiniJavaSemantics.Visitable visitable) {
        return new ExpressionList();
    }

    @Override
    public Instruction visitARG_LIST1(MiniJavaSemantics.Visitable visitable) {
        ExpressionList list = (ExpressionList) visitable.getVisitable(0).accept(this);
        list.getList().add((Expression) visitable.getVisitable(1).accept(this));
        return list;
    }


    public List<FunctionModule<Statement>> getFunctions() {
        return functions;
    }
}
