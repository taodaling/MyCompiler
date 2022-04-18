package org.dalingtao.sa.sample.ast;

import org.dalingtao.parser.CreateAstNode;

import java.io.IOException;

public class MiniJavaSemanticCreator {
    public static void main(String[] args) throws IOException {
        CreateAstNode.writeFile("D:\\source\\MyCompiler\\SemanticAnalysis\\src\\main\\java\\org\\dalingtao\\sa\\sample\\ast\\MinJavaSemantics.java",
                "D:\\source\\MyCompiler\\SemanticAnalysis\\src\\main\\resources\\MiniJava.parser");

    }
}
