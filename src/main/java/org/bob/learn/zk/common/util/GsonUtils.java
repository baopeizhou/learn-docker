package org.bob.learn.zk.common.util;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public final class GsonUtils {

    private static Gson gson = new Gson();


    public static <T> String toJson(T t) {
        String s = null;
        if (t != null) {
            s = gson.toJson(t);
        }
        return s;

    }

    public static <T> T fromJson(String s, Class<T> clazz) {
        T t = null;
        if (StringUtils.isNoneBlank(s)) {
            t = gson.fromJson(s, clazz);
        }
        return t;
    }

}
