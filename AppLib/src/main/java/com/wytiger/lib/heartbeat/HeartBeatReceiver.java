package com.wytiger.lib.heartbeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 心跳广播接收者
 * 
 * @author wytiger
 * @date 2016-5-27
 */
public class HeartBeatReceiver extends BroadcastReceiver {
	private static final String TAG = "HeartBeatReceiver";
	private static long count = 0;

	@Override
	public void onReceive(Context ctx, Intent intent) {

		int value = intent.getIntExtra(HeartBeatManager.ACTION_HeartBeat, -1);
		if (value == HeartBeatManager.VALUE_HeartBeatAlarm) {
			count++;
			Log.i(TAG, "heart beat" + count);
		}
	}
}