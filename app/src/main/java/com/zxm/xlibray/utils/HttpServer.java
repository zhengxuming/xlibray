package com.zxm.xlibray.utils;


import com.orhanobut.logger.Logger;
import com.steven.baselibrary.utils.Preferences;
import com.steven.baselibrary.utils.network.ParamsString;
import com.zxm.xlibray.bean.BaseBean;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by Steven on 2017/8/3.
 */

public class HttpServer {

    private static CommonApi commonApi = HttpRequest.INSTANCE.getCommonApi();

//    public static Observable<BaseBean> getParams(ParamsString bean) {
//        LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
//        tempMap.putAll(bean.value);
//        tempMap.put("device_type","android");
//
//        Logger.t("lx").i("token--->%s", Preferences.getLoginToken());
//        return commonApi.postBody(Preferences.getLoginToken(),bean.getPath(),tempMap);
//    }

    /**
     * 获取加密key
     *
     * @return
     */
    public static Map<String, String> getAuthSignedParameters(ParamsString bean) {
        LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
        tempMap.putAll(bean.value);
        tempMap.put("device_type","android");
        return tempMap;
    }

}
