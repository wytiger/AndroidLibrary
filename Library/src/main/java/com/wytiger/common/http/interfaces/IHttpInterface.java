package com.wytiger.common.http.interfaces;

import java.util.Map;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public interface IHttpInterface {
    void get(String baseUrl, Map<String, Object> params, IHttpCallback httpCallback);

    void post(String baseUrl, Map<String, Object> params, IHttpCallback httpCallback);
}
