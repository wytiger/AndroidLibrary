package com.wytiger.common.http;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public interface IHttpInterface {
    void get(String url, IHttpCallback httpCallback);

    void post(String url, String requestBody, IHttpCallback httpCallback);
}
