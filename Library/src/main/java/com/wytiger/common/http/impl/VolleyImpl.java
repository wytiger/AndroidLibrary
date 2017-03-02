package com.wytiger.common.http.impl;

import android.app.Application;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wytiger.common.http.interfaces.IHttpCallback;
import com.wytiger.common.http.interfaces.IHttpInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * description:Volley实现get  post请求
 * Created by wytiger on 2016-12-22.
 */

public class VolleyImpl implements IHttpInterface {
    public static RequestQueue mQueue;


    public static VolleyImpl getInstance(Application appContext) {
        mQueue = Volley.newRequestQueue(appContext);
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final VolleyImpl sInstance = new VolleyImpl();
    }

    @Override
    public void get(String baseUrl, final Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback.onStart();
        //响应监听器
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestCallback.onSuccess(s);
                requestCallback.onFinish();
            }
        };
        //错误监听器
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestCallback.onFailure(volleyError);
                requestCallback.onFinish();
            }
        };
        //String请求
        StringRequest request = new StringRequest(Request.Method.GET, baseUrl, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                if (null != params) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        map.put(entry.getKey(), entry.getValue().toString());
                    }
                }
                return map;
            }
        };
        //加入请求队列
        mQueue.add(request);
    }

    @Override
    public void post(String baseUrl, final Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback.onStart();
        //响应监听器
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestCallback.onSuccess(s);
                requestCallback.onFinish();
            }
        };
        //错误监听器
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestCallback.onFailure(volleyError);
                requestCallback.onFinish();
            }
        };
        //String请求
        StringRequest request = new StringRequest(Request.Method.POST, baseUrl, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                if (null != params) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        map.put(entry.getKey(), entry.getValue().toString());
                    }
                }
                return map;
            }
        };
        //加入请求队列
        mQueue.add(request);
    }


}