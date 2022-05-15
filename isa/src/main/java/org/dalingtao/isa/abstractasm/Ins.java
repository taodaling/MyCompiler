package org.dalingtao.isa.abstractasm;

import org.dalingtao.StringReader;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ins {
    static String[] empty = new String[0];
    String[] data;
    String[] def = empty;
    String[] use = empty;

    public Ins(String... data) {
        this.data = data;
    }

    public void setDef(String... def) {
        this.def = def;
    }

    public void setUse(String... use) {
        this.use = use;
    }

    public String[] getDef() {
        return def;
    }

    public String[] getUse() {
        return use;
    }


    String map(String ins, Map<String, String> color) {
        StringReader reader = new StringReader(ins);
        StringBuilder out = new StringBuilder();
        while (reader.peek() != -1) {
            int first = reader.read();
            if (first == 'T') {
                //cool
                StringBuilder rep = new StringBuilder();
                rep.append((char) first);
                while (reader.peek() >= '0' && reader.peek() <= '9' || reader.peek() >= 'A' && reader.peek() <= 'Z') {
                    rep.append((char) reader.read());
                }
                out.append(color.get(rep.toString()));
            } else {
                out.append((char) first);
            }
        }
        return out.toString();
    }

    public void repaint(Map<String, String> color) {
        for (int i = 0; i < data.length; i++) {
            data[i] = map(data[i], color);
        }
        for (int i = 0; i < def.length; i++) {
            def[i] = map(def[i], color);
        }
        for (int i = 0; i < use.length; i++) {
            use[i] = map(use[i], color);
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(data).map(Object::toString).collect(Collectors.joining(" "))
                + "#" + Arrays.stream(def).collect(Collectors.joining(",")) + "#" +
                Arrays.stream(use).collect(Collectors.joining(",")) + "\n";
    }

    public String toASM() {
        return Arrays.stream(data).map(Objects::toString).collect(Collectors.joining(" "));
    }
}
