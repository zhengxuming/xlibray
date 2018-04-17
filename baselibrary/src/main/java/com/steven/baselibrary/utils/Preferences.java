package com.steven.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


/**
 * SharedPreferences工具类
 * Created by wcy on 2015/11/28.
 */
public class Preferences {
    private static final String NIGHT_MODE = "night_mode";
    private static final String LOGIN_STATE = "login_state";
    private static final String USER_ID = "user_id";
    private static final String PUSH_TOGGLE = "push_state";
    private static final String SECRET_KEY = "secret_key";
    private static final String ACCESS_KEY = "access_key";
    private static final String AUTH_KEY = "auth_key";
    private static final String USER_ACCOUNT = "account";
    private static final String USER_HEADER = "user_header";
    private static final String HISTORY_KEY = "key_history";
    private static final String SERVICE_TEL = "serviceTel";
    private static final String COUNT_DOWN = "count_down";
    private static final String LAST_TIME = "last_time";
    private static final String USER_NAME = "user_name";


    public static void setUserName(Context context,String name) {
        saveString(context,USER_NAME, name);
    }

    public static String getUserName(Context context) {
        return getString(context,USER_NAME, "");
    }

    public static void setLastTime(Context context,long lastTime) {
        saveLong(context,LAST_TIME, lastTime);
    }

    public static long getLastTime(Context context) {
        return getLong(context,LAST_TIME, 0);
    }

    public static void setLastCount(Context context,long lastCount) {
        saveLong(context,COUNT_DOWN, lastCount);
    }

    public static long getLastCount(Context context) {
        return getLong(context,COUNT_DOWN, 60000);
    }

    public static String getServiceTel(Context context) {
        return getString(context,SERVICE_TEL, "");
    }

    public static void setServiceTel(Context context,String tel) {
        saveString(context,SERVICE_TEL, tel);
    }

    public static String getHistoryKey(Context context) {
        return getString(context,HISTORY_KEY, "");
    }

    public static void setHistoryKey(Context context,String key) {
        saveString(context,HISTORY_KEY, key);
    }

    public static String getUserId(Context context) {
        return getString(context,USER_ID, "");
    }

    public static void setUserId(Context context,String id) {
        saveString(context,USER_ID, id);
    }

    public static String getSecretKey(Context context) {
        return getString(context,SECRET_KEY, "");
    }

    public static void setSecretKey(Context context,String key) {
        saveString(context,SECRET_KEY, key);
    }

    public static String getAccessKey(Context context) {
        return getString(context,ACCESS_KEY, "");
    }

    public static void setAccessKey(Context context,String key) {
        saveString(context,ACCESS_KEY, key);
    }

    public static void setUserHeader(Context context,String header) {
        saveString(context,USER_HEADER, header);
    }

    public static String getUserHeader(Context context) {
        return getString(context,USER_HEADER, "");
    }

    public static void setAuthKey(Context context,String key) {
        saveString(context,AUTH_KEY, key);
    }

    public static String getAuthKey(Context context) {
        return getString(context,AUTH_KEY, "");
    }

    public static void setAccount(Context context,String key) {
        saveString(context,USER_ACCOUNT, key);
    }

    public static String getAccount(Context context) {
        return getString(context,USER_ACCOUNT, "");
    }

    public static boolean getPushOpen(Context context) {
        return getBoolean(context,PUSH_TOGGLE, true);
    }

    public static void setPushOpen(Context context,boolean push) {
        saveBoolean(context,PUSH_TOGGLE, push);
    }

    public static boolean isLogin(Context context) {
        return getBoolean(context,LOGIN_STATE, false);
    }

    public static void setIsLogin(Context context,boolean islogin) {
        saveBoolean(context,LOGIN_STATE, islogin);
    }

    public static boolean isNightMode(Context context) {
        return getBoolean(context,NIGHT_MODE, false);
    }

    public static void saveNightMode(Context context,boolean on) {
        saveBoolean(context,NIGHT_MODE, on);
    }

    private static boolean getBoolean(Context context,String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    private static void saveBoolean(Context context,String key, boolean value) {
        getPreferences(context).edit().putBoolean(key, value).apply();
    }

    private static int getInt(Context context,String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    private static void saveInt(Context context,String key, int value) {
        getPreferences(context).edit().putInt(key, value).apply();
    }

    private static long getLong(Context context,String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    private static void saveLong(Context context,String key, long value) {
        getPreferences(context).edit().putLong(key, value).apply();
    }

    private static String getString(Context context,String key, @Nullable String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    private static void saveString(Context context,String key, @Nullable String value) {
        getPreferences(context).edit().putString(key, value).apply();
    }

    private static SharedPreferences getPreferences(Context sContext) {
        return PreferenceManager.getDefaultSharedPreferences(sContext);
    }

}
