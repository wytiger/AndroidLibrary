package com.wytiger.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnHttp).setOnClickListener(this);
        findViewById(R.id.btnSpannable).setOnClickListener(this);
//        AsyncHttpClient httpClient = new AsyncHttpClient();
//        httpClient.get("www.baidu.com", null, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, org.apache.http.Header[] headers, String s, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int i, org.apache.http.Header[] headers, String s) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHttp:
                startActivity(new Intent(this, HttpDemoActivity.class));
                break;
            case R.id.btnSpannable:
                startActivity(new Intent(this, SpannableActivity.class));
                break;

            default:
                break;
        }

    }

}
