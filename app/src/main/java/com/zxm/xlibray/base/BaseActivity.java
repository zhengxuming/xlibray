package com.zxm.xlibray.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.steven.baselibrary.App;
import com.steven.baselibrary.utils.NetworkUtils;
import com.steven.baselibrary.utils.ScreenUtils;
import com.steven.baselibrary.utils.network.BaseApi;
import com.steven.baselibrary.utils.network.HttpCallback;
import com.steven.baselibrary.utils.network.HttpUtil;
import com.steven.baselibrary.widget.MyToast;
import com.steven.baselibrary.widget.ProgressDialog;
import com.steven.baselibrary.widget.TitleBar;
import com.zxm.xlibray.R;
import com.zxm.xlibray.bean.BaseBean;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Steven on 2017/7/13.
 */

public abstract class BaseActivity extends FragmentActivity {

    public int layoutId = 0;
    public TitleBar titleBar;
    public Context context;
    protected Subscription subscription;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        context = this;
        App.getInstance().addActivity(this);
        initSystemBar();

        if (progressDialog == null) {
            progressDialog = new ProgressDialog.Builder(this)
                    .create();
        }
    }

    private void initContentView() {
        layoutId = setLayoutId();
        if (layoutId > 0) {
            super.setContentView(layoutId);
            titleBar = findViewById(com.steven.baselibrary.R.id.title_bar);
        }
    }

    public abstract int setLayoutId();

    public void onRefresh(){}

    /**
     * 沉浸式状态栏
     */
    public void initSystemBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (layoutId > 0 && null != titleBar) {
                int top = ScreenUtils.getSystemBarHeight(this);
                titleBar.setBackgroundColor(ContextCompat.getColor(this, com.steven.baselibrary.R.color.main_theme_color));
                titleBar.requestLayout();
                titleBar.setPadding(0, top, 0, 0);
            }
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (layoutId > 0 && null != titleBar) {
                int top = ScreenUtils.getSystemBarHeight(this);
                titleBar.setBackgroundColor(ContextCompat.getColor(this, com.steven.baselibrary.R.color.main_theme_color));
                titleBar.requestLayout();
                titleBar.setPadding(0, top, 0, 0);
            }
            // KITKAT解决方案
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public View getContentView() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        FrameLayout content = view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }

    public BaseApi getApi() {
        return HttpUtil.getRequest().create(BaseApi.class);
    }

    public <T> void getHttpRequest(final Observable observable, final HttpCallback callBack) {

        if (!NetworkUtils.isNetworkConnected(this)) {
            callBack.onErro("网络异常，请稍后重试");
            return;
        }

        sendRequest(observable, new Subscriber<BaseBean>() {

            @Override
            public void onStart() {
                super.onStart();
                callBack.onStart();
            }

            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                if (e.getMessage().contains("timeout")){
                    callBack.onErro("网络异常，请稍后重试");
                }else{
                    callBack.onErro(e.getMessage());
                }
                if (null!=progressDialog){
                    progressDialog.dismiss();
                }
                Logger.t("lx").i("onError: " + e.getMessage());
            }

            @Override
            public void onNext(BaseBean response) {
                Logger.t("lx").i("onNext: ---->" + new Gson().toJson(response));
                if (null!=progressDialog){
                    progressDialog.dismiss();
                }
                if (response.getStatus().equals("ok")) {
                    callBack.onSuccess(response.getData());
                } else {
                    if (response.getErr_code().equals("invalid_token") || response.getErr_code().equals("unauthorized_request")) {
//                        Preferences.setLoginToken("");
//                        openActivity(LoginWebActivity.class, LoginWebActivity.ACT_FROM, "token");
                        finish();
                        return;
                    }
                    if (response.getErr_msg().contains("timeout")){
                        callBack.onErro("网络异常，请稍后重试");
                    }else{
                        callBack.onErro(response.getErr_msg());
                    }
                }
            }
        });
    }

    private void sendRequest(final Observable observable, final Subscriber observer) {
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    /**
     * 跳转activity
     */
    public void openActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    /**
     * 跳转activity
     */
    public void openActivity(Class clazz) {
        openActivity(new Intent(this, clazz));
    }

    /**
     * 跳转activity
     */
    public void openActivitys(Class... classes) {
        Intent[] intents = new Intent[classes.length];
        for (int i = 0; i < classes.length; i++) {
            Intent intent = new Intent(this, classes[i]);
            intents[i] = intent;
        }
        overridePendingTransition(com.steven.baselibrary.R.anim.right_in, com.steven.baselibrary.R.anim.left_out);
        startActivities(intents);
    }

    /**
     * 跳转activity
     *
     * @param clazz
     * @param parmas 传值格式  key,value
     */

    public void openActivity(Class clazz, Object... parmas) {
        if (parmas != null) {
            Intent i = new Intent(this, clazz);
            int parmasLenth = parmas.length;
            if (parmasLenth % 2 != 0) {
                MyToast.getInstance().showError(this,"参数格式为key,value");
            } else {
                parmasLenth = parmasLenth / 2;
                for (int j = 0; j < parmasLenth; j++) {
                    Object parmas1 = parmas[j + j];
                    Object parmas2 = parmas[j + j + 1];
                    if (parmas1 instanceof String) {
                        String key = (String) parmas1;
                        if (parmas2 instanceof String)
                            i.putExtra(key, (String) parmas2);
                        else if (parmas2 instanceof Integer)
                            i.putExtra(key, (int) parmas2);
                        else if (parmas2 instanceof Boolean)
                            i.putExtra(key, (boolean) parmas2);
                        else if (parmas2 instanceof Parcelable)
                            i.putExtra(key, (Parcelable) parmas2);

                    } else {
                        MyToast.getInstance().showError(this,"参数key类型不对");
                        return;
                    }
                }
            }
            openActivity(i);
        }
    }

    /**
     * 跳转activity
     */
    public void openActivityForResult(Class<?> cls, int requestCode) {
        openActivityForResult(new Intent(this, cls), requestCode);
    }

    /**
     * 跳转activity
     */
    public void openActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(com.steven.baselibrary.R.anim.right_in, com.steven.baselibrary.R.anim.left_out);
    }

    public void closeActivity() {
        finish();
        overridePendingTransition(com.steven.baselibrary.R.anim.right_in, com.steven.baselibrary.R.anim.left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.steven.baselibrary.R.anim.left_in, com.steven.baselibrary.R.anim.right_out);
    }

}
