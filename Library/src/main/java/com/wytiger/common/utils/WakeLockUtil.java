package com.wytiger.common.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

/**
 * 电源唤醒锁, 申请锁之后一定要释放锁. 需要权限：
 * 
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * 
 * @author wytiger
 * @date 2016-8-7
 */
public class WakeLockUtil {

	private static final String TAG = "WakeLockUtil";
	private static WakeLock wakeLock = null;

	/**
	 * 获取电源锁
	 * 
	 * @param context
	 * @param flags
	 * 
	 *            各种锁的类型对CPU 、屏幕、键盘的影响： 
	 *            PARTIAL_WAKE_LOCK:保持CPU运转，屏幕和键盘灯有可能是关闭的;
	 *            SCREEN_DIM_WAKE_LOCK：保持CPU 运转，允许保持屏幕显示但有可能是灰的，允许关闭键盘灯 ;
	 *            SCREEN_BRIGHT_WAKE_LOCK：保持CPU 运转，允许保持屏幕高亮显示，允许关闭键盘灯;
	 *            FULL_WAKE_LOCK：保持CPU 运转，保持屏幕高亮显示，键盘灯也保持亮度 ;
	 *            ACQUIRE_CAUSES_WAKEUP：并不会立即唤醒屏幕(通知除外), 而是使屏幕被唤醒后保持唤醒不休眠.
	 *            ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间.
	 */
	public static void acquireWakeLock(Context context, int flags) {
		if (null == wakeLock) {
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(flags, TAG);
			if (null != wakeLock) {
				wakeLock.acquire();
				Log.i(TAG, "获得电源锁");
			}
		}
	}

	
	/**
	 * 获取cpu唤醒锁,按电源键cpu也不休眠
	 * 
	 * @param context
	 */
	public static void acquireCPUWakeLock(Context context) {
		
		acquireWakeLock(context, PowerManager.PARTIAL_WAKE_LOCK);
	}

	/**
	 * 获取屏幕Screen唤醒锁, 屏幕全亮
	 * 
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public static void acquireScreenBrightWakeLock(Context context) {
		
		acquireWakeLock(context, PowerManager.SCREEN_BRIGHT_WAKE_LOCK);
	}

	/**
	 * 获取屏幕Screen唤醒锁, 屏幕灰色
	 * 
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public static void acquireScreenDimWakeLock(Context context) {
		
		acquireWakeLock(context, PowerManager.SCREEN_DIM_WAKE_LOCK);
	}
	
	/**
	 * 释放电源锁
	 */
	public static void releaseWakeLock() {
		if (null != wakeLock) {
			wakeLock.release();
			wakeLock = null;
			Log.i(TAG, "释放电源锁");
		}
	}


}
