package com.wytiger.lib.http;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public interface IHttpManager {
    void get(String url, HttpCallback httpCallback);

    void post(String url, String requestBody, HttpCallback httpCallback);
}
