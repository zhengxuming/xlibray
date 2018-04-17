// (c)2016 Flipboard Inc, All Rights Reserved.

package com.zxm.xlibray.bean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApi {

    @POST
    Observable<BaseBean<String>> getAuth(@Url String url, @Body Map<String, String> map);

//    @GET
//    Observable<BaseBean<ArrayList<ContractBean>>> getContractFolder(@Url String url);
//
//    @GET
//    Observable<BaseBean<MapBean>> getRecentContract(@Url String url);
//
//    @POST
//    Observable<BaseBean<ListBaseBean<ContractBean>>> getContractFiles(@Url String url, @Body Map<String, String> map);
//
//    @POST
//    Observable<BaseBean<Map<String, ListBaseBean<ContractBean>>>> getStateContract(@Url String url, @Body Map<String, String> map);
//
//    @POST("/v1/document/my/filed/{id}")
//    Observable<BaseBean<ListBaseBean<ContractBean>>> getContractById(@Path("id") String id, @Body Map<String, String> map);
//
//    @POST
//    Observable<BaseBean<ArrayList<ContractBean>>> searchContract(@Url String url, @Body Map<String, String> map);
//
//    @GET("/v1/user")
//    Observable<BaseBean<UserBean>> getUserInfo();

    @GET("/v1/user/is_enable_sign_password")
    Observable<BaseBean<Boolean>> needSignPwd();

    @POST("/v1/user_attr/set_verify_sign")
    Observable<BaseBean<String>> setVerifySign();

    @POST("/v1/user/verify_code/set_sign_password")
    Observable<BaseBean<String>> sendSetSignPwdCode();

    @POST("/v1/user_attr/set_sing_password")
    Observable<BaseBean<String>> setSignPwd(@Body Map<String, String> map);

    @POST("/v1/user/only_check_captcha2")
    Observable<BaseBean<Boolean>> checkCode(@Body Map<String, String> map);

    @GET("/v1/user/yonyou_logout")
    Observable<BaseBean<String>> quitAccount();

}
