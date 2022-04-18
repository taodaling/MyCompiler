package org.dalingtao.sa.sample.ast;

import org.dalingtao.Context;
import org.dalingtao.IOUtil;
import org.dalingtao.lexer.Lexer;
import org.dalingtao.lexer.LexerCompiler;
import org.dalingtao.parser.ParserLR;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RunSemanticAnalysis {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String code = IOUtil.readApplicationClassResource("MiniJava.code", StandardCharsets.ISO_8859_1);
        String lexer = IOUtil.readApplicationClassResource("MiniJava.lexer", StandardCharsets.ISO_8859_1);
        String parser = IOUtil.readApplicationClassResource("MiniJava.parser", StandardCharsets.ISO_8859_1);

        Context.getInstance().setCode(code);
        var tokens = Lexer.fromRule(lexer).lexer(code);
        System.out.println(tokens.getTokens());

        MinJavaSemantics.PROGRAM ast = (MinJavaSemantics.PROGRAM) ParserLR.fromRule(parser).parse(tokens);

        CreateSymbolTable createSymbolTable = new CreateSymbolTable();
        CheckTypeCompatible checkTypeCompatible = new CheckTypeCompatible();
        ast.accept(createSymbolTable);
        ast.accept(checkTypeCompatible);


    }
}
