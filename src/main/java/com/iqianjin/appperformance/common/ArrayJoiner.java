package com.iqianjin.appperformance.common;

import java.util.List;


public abstract class ArrayJoiner {

    private static final String EMPTY = "";
    private static final int DEFAULT_LENGTH = 32;
    private static final String DEFAULT_SEPARATOR = ",";

    public static String join(List list) {
        return join(list, DEFAULT_SEPARATOR);
    }

    public static String join(List list, String separator) {
        if (list==null || list.isEmpty()) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder(list.size() * DEFAULT_LENGTH);
        for (Object e : list) {
            if (e==null) {
                sb.append(EMPTY);
            } else {
                sb.append(e);
            }
            sb.append(separator);
        }
        //删除最后一个字符
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
