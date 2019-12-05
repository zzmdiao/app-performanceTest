package com.iqianjin.appperformance.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    public CollectionUtils() {
    }

    public static int size(Collection col) {
        return col != null ? col.size() : 0;
    }

    public static int size(Map map) {
        return map != null ? map.size() : 0;
    }

    public static boolean isEmpty(Collection col) {
        return col == null || col.isEmpty();
    }

    public static boolean isNotEmpty(Collection col) {
        return !isEmpty(col);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
