package org.dalingtao;

import java.util.Collection;
import java.util.Collections;

public class CollectionUtil {
    public static <T> boolean addAll(Collection<T> a, Collection<T> b) {
        int size = a.size();
        a.addAll(b);
        return size > a.size();
    }
}
