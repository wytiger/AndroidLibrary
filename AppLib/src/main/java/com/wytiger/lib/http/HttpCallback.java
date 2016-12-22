package com.wytiger.lib.http;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public interface HttpCallback {
    void onSuccess(String response);
    void onFailure(Throwable e);
}
