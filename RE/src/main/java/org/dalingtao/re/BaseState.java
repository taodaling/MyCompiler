package org.dalingtao.re;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseState implements State {
    int id;
    Map<String, Object> properties = new HashMap<>();
    List<Transfer> adj = new ArrayList<>();

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Transfer> adj() {
        return adj;
    }

    @Override
    public Transfer get(int c) {
        return null;
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}
