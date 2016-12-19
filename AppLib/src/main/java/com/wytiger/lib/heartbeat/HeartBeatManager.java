package com.wytiger.lib.heartbeat;

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
public class HeartBeatManager {
	private static final String TAG = "HeartBeatManager";
	public static final String ACTION_HeartBeat = "HeartBeat";
	public static final int VALUE_HeartBeatAlarm = 1;
	private static final int HEARTBEAT_ID = 0;

	private static long HEARTBEAT_INTERVAL = 60 * 1000;

	/**
	 * 开启心跳
	 * 
	 * @param mContext
	 */
	public static void startHeartBeat(Context mContext) {
		Intent intent = new Intent(mContext, HeartBeatReceiver.class);
		intent.putExtra(ACTION_HeartBeat, VALUE_HeartBeatAlarm);
		PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, HEARTBEAT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), HEARTBEAT_INTERVAL, pendIntent);
	}

	/**
	 * 停止心跳
	 * 
	 * @param mContext
	 */
	public static void stopHeartBeat(Context mContext) {
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

	/**
	 * 开启心跳
	 * 
	 * @param context
	 *            上下文
	 * @param intervalSeconds
	 *            间隔时间,单位秒
	 * @param cls
	 *            要启动的类,可以是BroadCastReceiver, Service, Activity
	 * @param intentAction
	 *            Intent对应的action,可以为null
	 */
	public static void startHeartBeat(Context context, int intervalSeconds, Class<?> cls, String intentAction) {
		// 获取AlarmManager系统服务
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		// 包装需要的Intent
		Intent intent = new Intent(context, cls);
		intent.setAction(intentAction);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 触发服务的起始时间
		long triggerAtTime = SystemClock.elapsedRealtime();

		// 使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, intervalSeconds * 1000, pendingIntent);
	}

	/**
	 * 停止心跳
	 * 
	 * @param context
	 *            上下文
	 * @param cls
	 *            要启动的类,可以是BroadCastReceiver, Service, Activity
	 * @param intentAction
	 *            Intent对应的action,可以为null
	 */
	public static void stopHeartBeat(Context context, Class<?> cls, String intentAction) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, cls);
		intent.setAction(intentAction);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 取消正在执行的服务
		manager.cancel(pendingIntent);
	}

}
