package org.dalingtao.isa.tile;

import org.dalingtao.StringReader;
import org.dalingtao.isa.abstractasm.Ins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class InsFactory {
    private Constructor c;
    List<String> args;

    public InsFactory(Constructor c, List<String> args) {
        this.c = c;
        this.args = args;
    }

    private String translate(String s, Map<String, String> map) {
        StringBuilder output = new StringBuilder();
        StringReader reader = new StringReader(s);
        StringBuilder rep = new StringBuilder();
        while (reader.peek() != -1) {
            int head = reader.read();
            if (head == '{') {
                //replace
                rep.setLength(0);
                while (reader.peek() != '}' && reader.peek() != -1) {
                    rep.append((char) reader.read());
                }
                if (reader.read() != '}') {
                    throw new TileCompilerException("Unmatched pair {} found in tile: " + s);
                }
                String res = map.get(rep.toString());
                if (res == null) {
                    throw new TileCompilerException("Not found matched alias {" + rep + "} for tile: " + s);
                }
                output.append(map.get(rep.toString()));
            } else {
                output.append((char) head);
            }
        }
        return output.toString();
    }

    public Ins get(Map<String, String> map) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return (Ins) c.newInstance(new Object[]{args.stream().map(x -> translate(x, map)).toArray(x -> new String[x])});
    }
}
