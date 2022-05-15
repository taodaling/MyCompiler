package org.dalingtao.isa.asm;

import org.dalingtao.isa.abstractasm.Ins;
import org.dalingtao.sa.st.FunctionDef;

import java.util.List;
import java.util.stream.Collectors;

public class GenASM {
    public String gen(List<Ins> instructs, FunctionDef def, int stack) {
        return def.getName() + ":\n" +
                "enter " + stack + ", 0\n" +
                instructs.stream().map(x -> x.toASM()).collect(Collectors.joining("\n"));
    }

}
