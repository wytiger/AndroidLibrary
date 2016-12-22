package com.wytiger.lib.http;

import android.app.Application;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public class HttpFactroy {
    public static IHttpManager getHttpManager(Application appContext) {
//        return VolleyManager.getInstance(appContext);
        return OkHttpManager.getInstance();
    }

}
