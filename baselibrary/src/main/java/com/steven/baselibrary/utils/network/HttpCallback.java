package com.steven.baselibrary.utils.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Steven on 2017/7/13.
 */

public abstract class HttpCallback<T> {

    public <T> Class<T> getChildClass(){
        Class<T> clazz;
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type tempType=params[0];
        if (tempType instanceof ParameterizedType&&((ParameterizedType) tempType).getRawType() == ArrayList.class){
            clazz =  (Class<T>)(((ParameterizedType) params[0]).getActualTypeArguments()[0]);
        }else{
            clazz=(Class)params[0];
        }
        return clazz;
    }

    public abstract void onSuccess(T t);

    public void onErro(Throwable throwable){};

    public void onErro(String erro){};

    public void onCompleted(){}

    public void onStart(){}

    public void onProgress(int progress){}


}
