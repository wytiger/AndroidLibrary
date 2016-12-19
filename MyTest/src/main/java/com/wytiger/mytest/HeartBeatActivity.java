package com.wytiger.mytest;

import com.wytiger.mytest.heartbeat.HeartBeatUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HeartBeatActivity extends Activity {
	private static final String TAG = "HeartBeatActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heart_beat);
		
		HeartBeatUtil.startHeartBeatAlarm(this);
		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HeartBeatUtil.stopHeartBeatAlarm(this);
		Log.i(TAG, "onDestroy");
	}

}
