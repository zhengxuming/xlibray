package com.zxm.xlibray.utils;

import com.steven.baselibrary.utils.network.HttpUtil;

/**
 * Created by Steven on 2017/8/3.
 */

public enum HttpRequest {

    INSTANCE;

    private CommonApi commonApi;

    HttpRequest(){
        commonApi= HttpUtil.getRequest().create(CommonApi.class);
    }

    public CommonApi getCommonApi(){ return commonApi; }

}
