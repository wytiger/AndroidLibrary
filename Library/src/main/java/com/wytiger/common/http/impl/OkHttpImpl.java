package com.wytiger.common.http.impl;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.wytiger.common.http.interfaces.IHttpCallback;
import com.wytiger.common.http.interfaces.IHttpInterface;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * description:OkHttp实现get  post请求
 * Created by wytiger on 2016-12-22.
 */

public class OkHttpImpl implements IHttpInterface {
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static Context appContext;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpImpl() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        //在哪个线程创建该对象，则最后的请求结果将在该线程回调
        handler = new Handler(appContext.getMainLooper());
    }

    private static class SingletonHolder {
        private static final OkHttpImpl INSTANCE = new OkHttpImpl();
    }

    public static OkHttpImpl getInstance(Context context) {
        appContext = context.getApplicationContext();
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void get(String baseUrl, Map<String, Object> params, final IHttpCallback httpCallback) {
        httpCallback.onStart();
        String url = baseUrl + getKeyValue(params);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(httpCallback, request);
    }

    @Override
    public void post(String baseUrl, Map<String, Object> params, IHttpCallback httpCallback) {
        httpCallback.onStart();
        RequestBody body = createRequestBody(params);
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();
        addCallBack(httpCallback, request);
    }

    private void addCallBack(final IHttpCallback requestCallback, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestCallback.onFailure(e);
                        requestCallback.onFinish();
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String json = response.body().string();
                                requestCallback.onSuccess(json);
                                requestCallback.onFinish();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.onFailure(new IOException(response.message() + ",url=" + call.request().url().toString()));
                            requestCallback.onFinish();
                        }
                    });


                }
            }
        });
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

    @NonNull
    private static RequestBody createRequestBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        return builder.build();
    }


}
