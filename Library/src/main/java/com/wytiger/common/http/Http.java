package com.wytiger.common.http;

import android.content.Context;

import com.wytiger.common.http.impl.VolleyImpl;
import com.wytiger.common.http.interfaces.IHttpInterface;

/**
 * description:策略模式封装http框架
 * Created by wytiger on 2016-12-22.
 */

public class Http {
    public static IHttpInterface getHttp(Context context) {
        return VolleyImpl.getInstance(context.getApplicationContext());
//        return OkHttpImpl.getInstance(context.getApplicationContext());
    }

}
