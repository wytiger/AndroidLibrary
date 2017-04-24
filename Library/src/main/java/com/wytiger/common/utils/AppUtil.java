package com.wytiger.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Parcelable;
import android.util.Log;
import android.view.WindowManager;

import java.util.List;

/**
 * App工具类
 *
 * @author wytiger
 * @date 2016.01.22
 */
public class AppUtil {
	private static final String TAG = "AppUtil";

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	//版本号
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 指定的activity所属的应用，是否是当前手机的顶级
	 *
	 * @param context
	 *            activity界面或者application
	 * @return 如果是，返回true；否则返回false
	 */
	@SuppressWarnings("deprecation")
	public static boolean isTopApp(Context context) {
		if (context == null) {
			return false;
		}

		try {
			String packageName = context.getPackageName();
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
			if (tasksInfo.size() > 0) {
				// 应用程序位于堆栈的顶层
				if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
					return true;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "e = " + e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * 判断指定activity是否是顶层activity
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isTopActivity(Activity activity, String activityName) {
		boolean isTop = false;
		ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		Log.d(TAG, "isTopActivity =" + cn.getClassName());
		if (cn.getClassName().contains(activityName)) {
			isTop = true;
		}
		Log.d(TAG, "isTop =" + isTop);
		return isTop;
	}

	public static void bringActivityToFront(Activity activity) {
		Log.i(TAG, activity.getClass().getSimpleName() + ":拉到前台");
		activity.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * 创建快捷方式
	 *
	 * @param cxt
	 *            Context
	 * @param icon
	 *            快捷方式图标
	 * @param title
	 *            快捷方式标题
	 * @param cls
	 *            要启动的类
	 */
	public void createDeskShortCut(Context cxt, int icon, String title, Class<?> cls) {
		// 创建快捷方式的Intent
		Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutIntent.putExtra("duplicate", false);
		// 需要现实的名称
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 快捷图片
		Parcelable ico = Intent.ShortcutIconResource.fromContext(cxt.getApplicationContext(), icon);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);


		Intent intent = new Intent(cxt, cls);
		// 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		// 点击快捷图片，运行的程序主入口
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 发送广播。OK
		cxt.sendBroadcast(shortcutIntent);
	}
}
