package com.wytiger.common.http.impl;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wytiger.common.http.interfaces.IHttpCallback;
import com.wytiger.common.http.interfaces.IHttpInterface;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * description:Volley实现get  post请求
 * Created by wytiger on 2016-12-22.
 */

public class VolleyImpl implements IHttpInterface {
    public static RequestQueue mQueue;


    public static VolleyImpl getInstance(Context appContext) {
        mQueue = Volley.newRequestQueue(appContext);
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final VolleyImpl sInstance = new VolleyImpl();
    }

    @Override
    public void get(String baseUrl, final Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback.onStart();
        String url = baseUrl + getKeyValue(params);
        //响应监听器
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestCallback.onSuccess(s);
                requestCallback.onEnd();
            }
        };
        //错误监听器
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestCallback.onFailure(volleyError);
                requestCallback.onEnd();
            }
        };
        //String请求
        StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<String, String>();
//                if (null != params) {
//                    for (Map.Entry<String, Object> entry : params.entrySet()) {
//                        map.put(entry.getKey(), entry.getValue().toString());
//                    }
//                }
//                return map;
//            }
        };
        //加入请求队列
        mQueue.add(request);
    }

    @Override
    public void post(String baseUrl, final Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback.onStart();
        String url = baseUrl + getKeyValue(params);
        //响应监听器
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestCallback.onSuccess(s);
                requestCallback.onEnd();
            }
        };
        //错误监听器
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestCallback.onFailure(volleyError);
                requestCallback.onEnd();
            }
        };
        //String请求
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<String, String>();
//                if (null != params) {
//                    for (Map.Entry<String, Object> entry : params.entrySet()) {
//                        map.put(entry.getKey(), entry.getValue().toString());
//                    }
//                }
//                return map;
//            }
        };
        //加入请求队列
        mQueue.add(request);
    }

    /**
     * 将请求参数map转换为name1=value1&name2=value2形式
     *
     * @param params
     * @return
     */
    public static String getKeyValue(Map<String, Object> params) {
        if (params == null) {
            return "";
        }
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("?");
            if (params != null) {
                Iterator<String> iterator = params.keySet().iterator();
                while (iterator.hasNext()) {
                    final String key = iterator.next();
                    builder.append(key);
                    builder.append("=");
                    builder.append(params.get(key));
                    builder.append("&");
                }
                if (params.keySet().size() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}