package com.wytiger.lib.utils.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class T {
	public static boolean isShow = true;

	private T() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void show(Context context, CharSequence msg) {
		if (isShow)
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param resId
	 */
	public static void show(Context context, int resId) {
		if (isShow)
			Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showLong(Context context, CharSequence msg) {
		if (isShow)
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showLong(Context context, int resId) {
		if (isShow)
			Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

}