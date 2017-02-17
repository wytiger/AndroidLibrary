package com.wytiger.common.keeplive;

import com.wytiger.common.utils.ServiceUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * app保活管理器。 1，监听app退到后台后启动一个1像素的activity，提升进程优先级。
 * 2，利用系统漏洞，设置一个无图标的通知，提升服务所在进程优先级
 * 
 * @author wytiger
 * @date 2016-8-18
 */
public class KeepLiveManager {
	public static final String TAG = "KeepLiveManager";
	private OnePixelActivity onePixelActivity;

	private KeepLiveManager() {

	}

	private static class SingletonHolder {
		private static KeepLiveManager aliveManager = new KeepLiveManager();
	}

	public static KeepLiveManager getInstance() {

		return SingletonHolder.aliveManager;
	}

	// --------------------------1像素-----------------------------

	/**
	 * 初始化,将指定activity设置为1像素； 需要在清单文件设置透明，不出现在最近任务等.
	 * android:excludeFromRecents="true" android:exported="true"
	 * android:finishOnTaskLaunch="false" android:label="OnePixel"
	 * android:launchMode="singleInstance" android:process=":live"
	 * android:screenOrientation="portrait"
	 * android:theme="@android:style/Theme.Translucent.NoTitleBar"
	 * 
	 * @param activity
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public void initOnePixelActivity(OnePixelActivity onePixelActivity) {
		this.onePixelActivity = onePixelActivity;

		// 设置1像素透明窗口
		Window window = onePixelActivity.getWindow();
		window.setGravity(Gravity.START | Gravity.TOP);
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.x = 0;
		layoutParams.y = 0;
		layoutParams.width = 1;
		layoutParams.height = 1;
		window.setAttributes(layoutParams);
	}

	/**
	 * 启动一像素activity
	 * 
	 * @param context
	 * @param activity
	 */
	public void startOnePixelActivity(Context context) {
		Log.i(TAG, "KeepLiveManager startOnePixelActivity");
		Intent intent = new Intent(context, OnePixelActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 结束一像素activity
	 * 
	 * @param context
	 * @param activity
	 */
	public void finishOnePixelActivity() {
		if (onePixelActivity != null && !onePixelActivity.isFinishing()) {
			onePixelActivity.finish();
			Log.i(TAG, "KeepLiveManager finishOnePixelActivity");
		}
	}

	// --------------------------无图标的前台通知服务-----------------------------

	/**
	 * 设置为前台服务，提示服务优先级，防止被系统回收。 注意：两个服务都需要在清单文件注册，并且innerService必须设置为public
	 * 
	 * @param liveService
	 * @param innerService
	 */
	public void setForeground(Service liveService, Service innerService) {
		Log.i(TAG, "KeepLiveManager setForeground, liveService = " + liveService + ", innerService = " + innerService);
		int id = 1;
		if (liveService != null) {
			if (Build.VERSION.SDK_INT < 18) {
				liveService.startForeground(id, new Notification());

			} else {
				liveService.startForeground(id, new Notification());

				if (innerService != null) {
					innerService.startForeground(id, new Notification());
					// stopForeground(true);
					innerService.stopSelf();
				}
			}
		}
	}

	/**
	 * 设置为前台服务
	 * 
	 * @param service
	 */
	public void setServiceToForeground(Service service) {
		Log.i(TAG, "KeepLiveManager setServiceToForeground, service = " + service);
		ServiceUtil.setServiceToForeground(service);
	}

	// --------------------------5.0 JobScheduler-----------------------------

	/**
	 * 5.0之后的启动定时任务；
	 * 需要权限android.Manifest.permission.RECEIVE_BOOT_COMPLETED
	 * 为service配置权限：android:permission="android.permission.BIND_JOB_SERVICE"
	 * 
	 * @param pkg
	 * @param service
	 */
	@SuppressLint("InlinedApi")
	public int startJobScheduler(Context context, Class<? extends JobService> service) {
		Log.i(TAG, "KeepLiveManager startJobScheduler, service = " + service);
		int jobId = 1;
		try {
			ComponentName jobService = new ComponentName(context, service);
			JobInfo.Builder builder = new JobInfo.Builder(jobId, jobService);
			builder.setPeriodic(10);// 时间间隔
			builder.setPersisted(true);// 是否允许唤醒设备
			JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
			return jobScheduler.schedule(builder.build());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

}
