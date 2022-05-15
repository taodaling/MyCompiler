package org.dalingtao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {
    public static <T> boolean addAll(Collection<T> a, Collection<T> b) {
        int size = a.size();
        a.addAll(b);
        return size > a.size();
    }

    public static <E extends Collection> E merge(E a, E b) {
        if (a.size() < b.size()) {
            b.addAll(a);
            return b;
        } else {
            a.addAll(b);
            return a;
        }
    }

    public static <T> List<T> asArrayList(T... data) {
        List<T> ans = new ArrayList<>(data.length);
        for (T x : data) {
            ans.add(x);
        }
        return ans;
    }

    public static <T> List<T> asArrayList(List<T> data) {
        if (data instanceof ArrayList) {
            return data;
        } else {
            return new ArrayList<>(data);
        }
    }

}
