package com.steven.baselibrary.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steven.baselibrary.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

/**
 * Created by Steven on 2017/7/12.
 */

public class HttpUtil {

    public final static String BASE_URL_DEBUG = "http://t.b2bappapi.lx18d.cn/";
//    public final static String BASE_URL_DEBUG = "http://192.168.250.67/";//xujijie
//    public final static String BASE_URL_DEBUG = "http://192.168.250.5/";//zhongzhen
    public static final String BASE_H5_URL_DEBUG = "http://lx18d-h5.kaiweidian.com/";

    public final static String BASE_URL = "https://appapi.lx18d.cn/";
    public static final String BASE_H5_URL = "https://h5.lx18d.cn/";

    public final static int PAGE_SIZE = 10;
    private static OkHttpClient okHttpClient;
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static Subscription subscription;

    public static String getH5Url() {
        return LogUtils.APP_DBG ? BASE_H5_URL_DEBUG : BASE_H5_URL;
    }

    public static Retrofit getRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .create();

        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.client(okHttpClient)
//                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJavaCallAdapterFactory);
        retrofit.baseUrl(LogUtils.APP_DBG ? BASE_URL_DEBUG : BASE_URL);
        return retrofit.build();
    }

}
