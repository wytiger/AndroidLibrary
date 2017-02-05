package com.wytiger.lib.http;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public class VolleyManager implements IHttpManager {
    public static RequestQueue mQueue;


    public static VolleyManager getInstance(Application appContext) {
        mQueue = Volley.newRequestQueue(appContext);
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final VolleyManager sInstance = new VolleyManager();
    }

    @Override
    public void get(String url, final IHttpCallback requestCallback) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestCallback.onSuccess(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        requestCallback.onFailure(volleyError);
                    }
                });
        mQueue.add(request);
    }

    @Override
    public void post(String url, String requestBodyJson, final IHttpCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.POST);
    }


    /**
     * 封装带请求体的请求方法
     *
     * @param url             url
     * @param requestBodyJson Json string请求体
     * @param requestCallback 回调接口
     * @param method          请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final IHttpCallback requestCallback, int method) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestCallback.onSuccess(response != null ? response.toString() : null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestCallback.onFailure(error);
                    }
                });
        mQueue.add(request);
    }

}