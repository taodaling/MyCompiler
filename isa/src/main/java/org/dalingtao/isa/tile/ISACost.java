package org.dalingtao.isa.tile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ISACost {
    Map<String, Long> costs;

    public ISACost(InputStream is) throws IOException {
        Properties properties = new Properties();
        properties.load(is);
        costs = new HashMap<>();
        for (var entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            costs.put(key, Long.parseLong(value));
        }
    }

    public long cost(String key) {
        Long ans = costs.get(key);
        if (ans == null) {
            throw new TileCompilerException("Not support isa instruct: " + key);
        }
        return ans;
    }
}
