package com.steven.baselibrary.utils.network;

public class ParamsString extends Params<String> {
    public ParamsString(String var1) {
        this.value.put(METHOD, var1);
    }

    public ParamsString add(String var1, String var2) {
        super.value.put(var1, var2);
        return this;
    }
}