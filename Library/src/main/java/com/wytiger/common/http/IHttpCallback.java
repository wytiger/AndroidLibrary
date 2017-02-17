package com.wytiger.common.http;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public interface IHttpCallback {
    void onStart();
    void onSuccess(String response);
    void onFailure(Throwable e);
    void onFinish();
}
