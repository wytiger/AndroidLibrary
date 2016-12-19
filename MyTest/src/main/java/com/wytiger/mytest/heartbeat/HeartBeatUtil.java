package com.wytiger.mytest.heartbeat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * 心跳工具类
 * 
 * @author wytiger
 * @date 2016-8-7
 */
public class HeartBeatUtil {
	public static final String TAG = "HeartBeatUtil";
	public static final String ACTION_HEARTBEAT = "HeartBeat";
	public static final int VALUE_HEARTBEAT = 1;
	private static final int HEARTBEAT_ID = 0;

	private static long BEAT_INTERVAL = 10 * 1000;//心跳间隔

	public static void startHeartBeatAlarm(Context mContext) {
		Log.i(TAG, "startHeartBeatAlarm");
		
		Intent intent = new Intent(mContext, HeartBeatReceiver.class);
		intent.putExtra(ACTION_HEARTBEAT, VALUE_HEARTBEAT);
		PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, HEARTBEAT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), BEAT_INTERVAL, pendIntent);
	}

	public static void stopHeartBeatAlarm(Context mContext) {
		Log.i(TAG, "stopHeartBeatAlarm");
		try {
			Intent intent = new Intent(mContext, HeartBeatReceiver.class);

			PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, HEARTBEAT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pendIntent);
		} catch (Exception e) {
			Log.e(TAG, "e=" + e.getMessage());
			e.printStackTrace();
		}
	}

}
