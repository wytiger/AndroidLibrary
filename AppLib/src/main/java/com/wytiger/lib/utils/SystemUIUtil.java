package com.wytiger.lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

/**
 * 系统UI工具类, 获取状态栏,标题栏,导航栏高度
 *
 * @author wytiger
 * @date 2016-8-11
 */
public class SystemUIUtil {
	
	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int height = 0;
		try {
			int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
			height = resources.getDimensionPixelSize(resourceId);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return height;

		// Class<?> c = null;
		// Object obj = null;
		// Field field = null;
		// int x = 0, statusBarHeight = 0;
		// try {
		// c = Class.forName("com.android.internal.R$dimen");
		// obj = c.newInstance();
		// field = c.getField("status_bar_height");
		// x = Integer.parseInt(field.get(obj).toString());
		// statusBarHeight = context.getResources().getDimensionPixelSize(x);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return statusBarHeight;
	}

	/**
	 * 获取导航栏(底部虚拟键)高度
	 * @param context
	 * @return
	 */
	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int height = 0;
		try {
			int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
			height = resources.getDimensionPixelSize(resourceId);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return height;

		// Class<?> c = null;
		// Object obj = null;
		// Field field = null;
		// int x = 0, statusBarHeight = 0;
		// try {
		// c = Class.forName("com.android.internal.R$dimen");
		// obj = c.newInstance();
		// field = c.getField("navigation_bar_height");
		// x = Integer.parseInt(field.get(obj).toString());
		// statusBarHeight = context.getResources().getDimensionPixelSize(x);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return statusBarHeight;
	}

}
