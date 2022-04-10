package org.dalingtao.re;

import java.util.List;
import java.util.Map;

public interface State {
    Map<String, Object> getProperties();
    int getId();
    List<Transfer> adj();
    Transfer get(int c);

}
