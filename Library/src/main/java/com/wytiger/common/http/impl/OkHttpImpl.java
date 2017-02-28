package com.wytiger.common.http.impl;

import android.os.Handler;

import com.wytiger.common.http.IHttpCallback;
import com.wytiger.common.http.IHttpInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public class OkHttpImpl implements IHttpInterface {
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpImpl() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        //在哪个线程创建该对象，则最后的请求结果将在该线程回调
        handler = new Handler();
    }

    private static class SingletonHolder {
        private static final OkHttpImpl INSTANCE = new OkHttpImpl();
    }

    public static OkHttpImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void get(String url, final IHttpCallback httpCallback) {
       httpCallback. onStart();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(httpCallback, request);
    }

    @Override
    public void post(String url, String requestBody, IHttpCallback httpCallback) {
        httpCallback. onStart();
        RequestBody body = RequestBody.create(TYPE_JSON, requestBody);
        Request request = new Request.Builder()
                .url(url)
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
                    }
                });
                requestCallback.onFinish();
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
                        }
                    });

                    requestCallback.onFinish();
                }
            }
        });
    }
}
