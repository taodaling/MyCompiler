package org.dalingtao.sa.sample.ast;

import org.dalingtao.Context;
import org.dalingtao.IOUtil;
import org.dalingtao.lexer.Lexer;
import org.dalingtao.parser.ParserLR;
import org.dalingtao.sa.cfg.BasicBlock;
import org.dalingtao.sa.cfg.Trace;
import org.dalingtao.sa.ir.CanonicalStatement;
import org.dalingtao.sa.ir.Statement;
import org.dalingtao.ToStringXml;
import org.dalingtao.sa.st.FunctionModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class RunSemanticAnalysis {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loadMiniJava();
    }

    public static List<FunctionModule<List<Statement>>> loadMiniJava() throws IOException, ClassNotFoundException {
        String code = IOUtil.readApplicationClassResource("MiniJava.code", StandardCharsets.ISO_8859_1);
        String lexer = IOUtil.readApplicationClassResource("MiniJava.lexer", StandardCharsets.ISO_8859_1);
        String parser = IOUtil.readApplicationClassResource("MiniJava.parser", StandardCharsets.ISO_8859_1);

        Context.getInstance().setCode(code);
        var tokens = Lexer.fromRule(lexer).lexer(code);
        System.out.println(tokens.getTokens());

        MiniJavaSemantics.PROGRAM ast = (MiniJavaSemantics.PROGRAM) ParserLR.fromRule(parser).parse(tokens);

        CreateSymbolTable createSymbolTable = new CreateSymbolTable();
        CheckTypeCompatible checkTypeCompatible = new CheckTypeCompatible(createSymbolTable);
        CreateIR createIR = new CreateIR();

        ast.accept(createSymbolTable);
        ast.accept(checkTypeCompatible);
        ast.accept(createIR);
        List<FunctionModule<Statement>> fm = createIR.getFunctions();
        System.out.println(fm);
        List<FunctionModule<CanonicalStatement>> fmLower =
                fm.stream().map(x -> {
                    Context.getInstance().setScope(x.getF());
                    CanonicalStatement c = x.getItem().lowering();
                    return new FunctionModule<>(x.getF(), c);
                }).collect(Collectors.toList());
        //     ir.lowering();
        List<FunctionModule<List<Statement>>> fReorder = fmLower.stream()
                .map(x -> {
                    Context.getInstance().setScope(x.getF());
                    var blocks = BasicBlock.split(x.getItem());
                    var trace = new Trace().reorder(blocks);
                    return new FunctionModule<>(x.getF(), trace);
                }).collect(Collectors.toList());

        System.out.println(ToStringXml.toString("block", fmLower));
        System.out.println(ToStringXml.toString("trace", fReorder));

        return fReorder;
    }
}
