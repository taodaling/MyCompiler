package org.dalingtao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToStringXml {
    public static String toString(String name, Object data) {
        String body = null;
        Stream s = null;
        if (data instanceof List) {
            s = ((List) data).stream();
        } else if (data instanceof Object[]) {
            s = Arrays.stream((Object[]) data);
        }
        if (s != null) {
            body = (String) s.map(Object::toString)
                    .collect(Collectors.joining(""));
        } else {
            body = data.toString();
        }

        return "<" + name + ">" + body + "</" + name + ">";

    }
}
