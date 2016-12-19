package com.wytiger.mytest.heartbeat;

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
	public static long count = 0;

	@Override
	public void onReceive(Context ctx, Intent intent) {

		int value = intent.getIntExtra(HeartBeatUtil.ACTION_HEARTBEAT, -1);
		if (value == HeartBeatUtil.VALUE_HEARTBEAT) {
			count++;
			Log.i(TAG, "onReceive, heart beat: " + count);
			
			ctx.startService(new Intent(ctx, WriteService.class));
		}
	}
}