package org.dalingtao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiProperties {
    public Map<String, String> property = new HashMap<>();
    public Map<String, List<String>> items = new HashMap<>();

    public MultiProperties(InputStream is) throws IOException {
        load(is);
    }

    void load(InputStream is) throws IOException {
        items = new HashMap<>();
        var data = IOUtil.readLines(is, StandardCharsets.ISO_8859_1, true);
        data.stream()
                .map(x -> x.stripLeading())
                .filter(x -> x.startsWith(":"))
                .forEach(line -> {
                    int index = line.indexOf('=');
                    String key = line.substring(1, index);
                    String value = line.substring(index + 1);
                    property.put(key, value);
                });
        data = data.stream()
                .map(x -> x.stripLeading())
                .filter(x -> !x.startsWith(":"))
                .filter(x -> !x.startsWith("#"))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());

        for (int i = 0; i < data.size(); i++) {
            String key = data.get(i);
            List<String> value = items.getOrDefault(key, new ArrayList<>());
            while (i + 1 < data.size()) {
                String nextLine = data.get(i + 1);
                if (nextLine.startsWith("|")) {
                    i++;
                    value.add(nextLine.substring(1));
                } else {
                    break;
                }
            }
            items.put(key, value);
        }
    }
}
