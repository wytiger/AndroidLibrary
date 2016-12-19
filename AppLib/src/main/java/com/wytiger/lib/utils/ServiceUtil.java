package com.wytiger.lib.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class ServiceUtil {
	private static final String TAG = "ServiceUtil";
	private static final int GRAY_ALIVE_SERVICE_ID = 100;

	/**
	 * 将服务设置为前台服务,提升服务所属进程优先级, 防止被系统回收.
	 * 注意：两个服务都需要在清单文件注册，并且innerService必须设置为public
	 * 
	 */
	public static void setServiceToForeground(Service service) {
		Log.i(TAG, "setServiceToForeground, service = " + service);
		// API < 18
		if (Build.VERSION.SDK_INT < 18) {
			service.startForeground(GRAY_ALIVE_SERVICE_ID, new Notification());
		} else {
			// API >= 18
			service.startForeground(GRAY_ALIVE_SERVICE_ID, new Notification());
			// 再启动一个内部服务并马上停止
			Intent innerIntent = new Intent(service, InnerService.class);
			service.startService(innerIntent);
		}

	}

	/**
	 * 给 API >= 18 的平台上用的灰色保活手段, 记得在清单文件注册服务，并设置服务为public
	 */
	public static class InnerService extends Service {
		
		@Override
		public void onCreate() {
			super.onCreate();
			Log.i(TAG, "InnerService onCreate");
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			Log.i(TAG, "InnerService onStartCommand");
			startForeground(GRAY_ALIVE_SERVICE_ID, new Notification());
//			stopForeground(true);
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
	}

	/**
	 * 服务是否在运行
	 * 
	 * @param context
	 * @param serviceClass
	 * @return
	 */
	public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
		if (serviceList == null || serviceList.size() == 0) {
			return false;
		}

		for (RunningServiceInfo info : serviceList) {
			if (info.service.getClassName().equals(serviceClass.getName()))
				return true;
		}
		return false;
	}


}
