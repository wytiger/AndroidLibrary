package com.wytiger.common.http;

import android.app.Application;

/**
 * description:http框架的框架
 * Created by wytiger on 2016-12-22.
 */

public class Http {
    public static IHttpInterface getHttp(Application appContext) {
//        return VolleyManager.getInstance(appContext);
        return OkHttpManager.getInstance();
    }

}
