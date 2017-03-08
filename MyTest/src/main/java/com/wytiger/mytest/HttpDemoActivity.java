package com.wytiger.mytest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wytiger.common.http.Http;
import com.wytiger.common.http.interfaces.HttpCallback;
import com.wytiger.common.utils.common.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class HttpDemoActivity extends Activity implements View.OnClickListener {
    String url = "http://www.weather.com.cn/data/sk/101010100.html";
    String url2 = "http://wthrcdn.etouch.cn/weather_mini";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demo);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i("Log","onClick");
        LogUtil.i("LogUtil","onClick2");
        Log.i("HttpDemoActivity","com.wytiger.common.BuildConfig.DEBUG = " + com.wytiger.common.BuildConfig.DEBUG);
        Log.i("HttpDemoActivity","com.wytiger.mytest.BuildConfig.DEBUG = " + com.wytiger.mytest.BuildConfig.DEBUG);
        switch (v.getId()) {
            case R.id.button:
                Http.getHttp(this).get(url, null, new HttpCallback() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        LogUtil.i(Thread.currentThread().getName() + ": onStart");
                    }

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + response);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + e.getMessage());
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        LogUtil.i(Thread.currentThread().getName() + ": onEnd");
                    }
                });
                break;
            case R.id.button2:
                Map<String, Object> map = new HashMap<>();
                map.put("citykey", "101210101");
                Http.getHttp(this).get(url2, map, new HttpCallback() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        LogUtil.i(Thread.currentThread().getName() + ": onStart");
                    }

                    @Override
                    public void onSuccess(String response) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + response);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + e.getMessage());
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        LogUtil.i(Thread.currentThread().getName() + ": onEnd");
                    }
                });
                break;

            case R.id.button3:
                Map<String, Object> map2 = new HashMap<>();
                map2.put("citykey", "101210101");
                Http.getHttp(this).post(url2, map2, new HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + response);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.i(Thread.currentThread().getName() + ": " + e.getMessage());
                    }
                });
                break;

            default:
                break;
        }

    }
}
