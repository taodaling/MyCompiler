package org.dalingtao.parser;

import org.dalingtao.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public class CreateAstNode extends BaseParserCompiler {
    public CreateAstNode(InputStream is) throws IOException {
        super(is);
    }

    public String replace(String s, String p, String rep) {
        return s.replaceAll(p, Matcher.quoteReplacement(rep));
    }

    @Override
    public void compile(OutputStream os) throws IOException {
        String fullClassName = properties.get("classFile");
        String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        String packageName = fullClassName.substring(0, fullClassName.lastIndexOf('.'));

        String nonTerminalTemplate = readFile("template/NonTerminal.template");
        String productionTemplate = readFile("template/Production.template");
        String visitableTemplate = readFile("template/Visitable.template");
        String visitorTemplate = readFile("template/Visitor.template");
        String visitorMethodTemplate = readFile("template/VisitorMethod.template");
        String skeletonTemplate = readFile("template/skeleton.template");

        StringBuilder body = new StringBuilder();
        StringBuilder methods = new StringBuilder();

        body.append(visitableTemplate).append("\n");
        for (var nt : nonTerminals) {
            body.append(replace(nonTerminalTemplate, "\\{ClassName}", nt.name)).append("\n");
            for (int i = 0; i < nt.productions.size(); i++) {
                var p = nt.productions.get(i);
                String pName = nt.name + i;
                body.append(replace(replace(replace(productionTemplate, "\\{ClassName}", pName)
                        , "\\{FatherClassName}", nt.name)
                        , "\\{Comment}", p.toString())).append("\n");
                methods.append(replace(replace(visitorMethodTemplate, "\\{Comment}", p.toString()),
                        "\\{ClassName}", pName)).append("\n");
            }
        }

        body.append(replace(visitorTemplate, "\\{Content}", methods.toString())).append("\n");

        String ans = replace(replace(replace(skeletonTemplate, "\\{Content}", body.toString())
                , "\\{packageName}", packageName), "\\{ClassName}", className);
        IOUtil.writeAll(os, ans.getBytes(StandardCharsets.UTF_8));
        os.flush();
    }

    String readFile(String path) {
        try (var is = this.getClass().getClassLoader().getResourceAsStream(path)) {
            return IOUtil.readAll(is, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String loadSample() throws IOException {
        try (var is = CreateAstNode.class.getClassLoader().getResourceAsStream("sample.parser")) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            new CreateAstNode(is).compile(bos);
            return bos.toString(StandardCharsets.UTF_8);
        }
    }

    public static void writeFile(String file, String parserConfig) throws IOException {
        try (var os = new FileOutputStream(file)) {
            try (var is = new FileInputStream(parserConfig)) {
                new CreateAstNode(is).compile(os);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //System.out.print(loadSample());
        writeFile("D:\\source\\MyCompiler\\Parser\\src\\main\\java\\org\\dalingtao\\sample\\Factory.java", "D:\\source\\MyCompiler\\Parser\\src\\main\\resources\\sample.parser");
    }
}
