package com.wytiger.lib.utils;

import android.app.Activity;
import android.app.Service;
import android.media.AudioAttributes;
import android.os.Vibrator;


/**
 * 手机震动工具类(马达)
 * @author wytiger
 * @date 2016年2月18日
 */
public class VibratorUtil {
	private VibratorUtil() {
		// 无法实例化
	}

	/**
	 * final Activity activity ：调用该方法的Activity实例
	 * long milliseconds ：震动的时长，单位是毫秒
	 */

	public static void Vibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}
	
	
	/**
	 * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
	 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
	
	/**
	 * 
	 * @param activity
	 * @param milliseconds
	 * @param attributes
	 */
	public static void Vibrate(final Activity activity, long milliseconds,  AudioAttributes attributes) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds, attributes);
	}

}
