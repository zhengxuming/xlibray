package com.zxm.xlibray.base;

/**
 * Created by zxm on 2017/12/4.
 */

public class CommonUrl {

    public final static String BASE_URL_DEBUG = "http://test251.bosign.cn:9090/";
    public final static String BASE_URL = "http://test251.bosign.cn:9090/";
    public static final String BASE_PIC_URL = "http://test251.bosign.cn";

    /**
     * 获取token
     */
    public static final String GET_AUTH = BASE_URL + "common/yonyou_login";

    /**
     * 获取我的合同文件夹
     */
    public static final String GET_MY_CONTRACT_FOLDERS = BASE_URL + "v1/document/my/labels";

    /**
     * 获取我的合同文件
     */
    public static final String GET_MY_CONTRACT_FILES = BASE_URL + "v1/document/my/not_filed";

    /**
     * 获取近期合同
     */
    public static final String GET_RECENT_CONTRACT = BASE_URL + "/v1/document/timeline";

    /**
     * 获取状态合同
     */
    public static final String GET_STATE_CONTRACT = BASE_URL + "/v1/document/state/index";

    /**
     * 搜索
     */
    public static final String SEARCH_CONTRACT = BASE_URL + "/v1/document/search";

    /**
     * 获取用户信息
     */
    public static final String GET_USER_INFO = BASE_URL + "/v1/user";

}
