package com.zxm.xlibray.utils;



import com.zxm.xlibray.bean.BaseBean;

import java.util.LinkedHashMap;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Steven on 2017/8/3.
 */

public interface CommonApi {

    @POST
    Observable<BaseBean> postBody(@Header("Token") String header, @Url String url, @Body LinkedHashMap<String, String> body);

}
