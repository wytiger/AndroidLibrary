package com.wytiger.common.http;

import android.app.Application;

/**
 * description:使用工厂模式设计框架的框架
 * Created by wytiger on 2016-12-22.
 */

public class HttpFactroy {
    public static IHttpManager getHttpManager(Application appContext) {
//        return VolleyManager.getInstance(appContext);
        return OkHttpManager.getInstance();
    }

}
