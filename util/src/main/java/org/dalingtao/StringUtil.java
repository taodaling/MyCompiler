package org.dalingtao;

import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<String> split(String s) {
        StreamReader reader = new StreamReader(new StringReader(s));
        List<String> ans = new ArrayList<>();
        try {
            while (reader.hasMore()) {
                ans.add(reader.read());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return ans;
    }
}
