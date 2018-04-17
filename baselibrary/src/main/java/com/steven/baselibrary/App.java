package com.steven.baselibrary;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.steven.baselibrary.utils.LogUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Steven on 2017/7/11.
 */

public class App extends Application {

    /**
     * 打开的activity
     **/
    private Set<Activity> activities = new HashSet<>();
    /**
     * 应用实例
     **/
    private static App instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static App getInstance() {
        return instance;
    }

    public void onCreate() {
        instance = this;
        super.onCreate();
        initData();
    }

    public void initData() {
        LogUtils.init(this);
//        LogUtils.APP_DBG=false;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activities.contains(activity)){
            return;
        }
        activities.add(activity);
    }

    public boolean hasActivity(String actName) {
        for (Activity activity : activities) {
            if (activity.getLocalClassName().contains(actName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param actName
     */
    public void finishActivity(String actName) {
        for (Activity activity : activities) {
            if (activity.getLocalClassName().contains(actName)) {
                activity.finish();
                activities.remove(activity);
                break;
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        finishActivity();
        System.exit(0);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivity() {
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
