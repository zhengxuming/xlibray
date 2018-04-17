package com.steven.baselibrary.utils.network;

import java.util.LinkedHashMap;
import java.util.Map;

public class Params<V> {
    public Map<String, V> value = new LinkedHashMap<>();
    public static String METHOD = "method";

    public Params() {
    }

    public Params add(String var1, V var2) {
        return this;
    }
}