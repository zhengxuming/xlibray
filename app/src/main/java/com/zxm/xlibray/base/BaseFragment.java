package com.zxm.xlibray.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.luck.picture.lib.rxbus2.Subscribe;
import com.luck.picture.lib.rxbus2.ThreadMode;
import com.orhanobut.logger.Logger;
import com.steven.baselibrary.utils.NetworkUtils;
import com.steven.baselibrary.utils.Preferences;
import com.steven.baselibrary.utils.ScreenUtils;
import com.steven.baselibrary.utils.network.BaseApi;
import com.steven.baselibrary.utils.network.HttpCallback;
import com.steven.baselibrary.utils.network.HttpUtil;
import com.steven.baselibrary.widget.MyToast;
import com.steven.baselibrary.widget.ProgressDialog;
import com.zxm.xlibray.bean.BaseBean;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Steven on 2017/7/12.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    protected Subscription subscription;
    public ProgressDialog progressDialog;
    public static final int LOGIN_CODE = 666;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);

        View rootView = view.findViewById(com.steven.baselibrary.R.id.root_view);
        if (null != rootView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int top = ScreenUtils.getSystemBarHeight(getActivity());
                rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rootView.setPadding(0, top, 0, 0);
            }
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog.Builder(getActivity())
                    .create();
        }
        initViews(view);
        return view;
    }

    public abstract void initViews(View view);

    public abstract int getLayoutId();

    private void sendRequest(final Observable observable, final Subscriber observer) {
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public BaseApi getApi() {
        return HttpUtil.getRequest().create(BaseApi.class);
    }

    public <T> void getHttpRequest(final Observable observable, final HttpCallback callBack) {

        if (!NetworkUtils.isNetworkConnected(getActivity())) {
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
                        ((BaseActivity) getActivity()).clearWebViewCache();
//                        Preferences.setLoginToken("");
//                        openActivity(LoginWebActivity.class, LoginWebActivity.ACT_FROM, "token");
                        getActivity().finish();
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

    private String getRunningActivityName() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (EventBus.getDefault().isRegistered(this)) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroyView();
    }

    public void onRefresh() {
    }

    /**
     * 跳转activity
     */
    public void openActivity(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(com.steven.baselibrary.R.anim.right_in, com.steven.baselibrary.R.anim.left_out);
    }

    /**
     * 跳转activity
     */
    public void openActivity(Class clazz) {
        openActivity(new Intent(getActivity(), clazz));
    }

    /**
     * 跳转activity
     *
     * @param clazz
     * @param parmas 传值格式  key,value
     */

    public void openActivity(Class clazz, Object... parmas) {
        if (parmas != null) {
            Intent i = new Intent(getActivity(), clazz);
            int parmasLenth = parmas.length;
            if (parmasLenth % 2 != 0) {
                MyToast.getInstance().showError(getActivity(),"参数格式为key,value");
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
                        MyToast.getInstance().showError(getActivity(),"参数key类型不对");
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
        openActivityForResult(new Intent(getActivity(), cls), requestCode);
    }

    /**
     * 跳转activity
     */
    public void openActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(com.steven.baselibrary.R.anim.right_in, com.steven.baselibrary.R.anim.left_out);
    }

    public void closeActivity() {
        getActivity().finish();
        getActivity().overridePendingTransition(com.steven.baselibrary.R.anim.left_in, com.steven.baselibrary.R.anim.right_out);
    }
}
