package com.wytiger.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;


/**
 * 包管理工具类
 * 
 * @author wytiger
 * @date 2016年1月22日
 */
public class PackageUtil {

	private static final String TAG = "PackageUtil";

	/**
	 * 获取应用程序的包名
	 * 
	 * @return 应用程序的包名
	 */
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 * 获取应用程序的版本号
	 * 
	 * @return 版本号
	 */
	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException exception) {
			Log.e(TAG, exception.toString());
		}
		return verCode;
	}

	/**
	 * 获取应用程序的外部版本号
	 * 
	 * @return 外部版本号
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException exception) {
			Log.e(TAG, exception.toString());
		}
		return versionName;
	}

	/**
	 * 获取手机品牌
	 * 
	 * @return
	 */
	public static String getBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取API等级（21、22、23 ...）
	 * 
	 * @return
	 */
	public static int getAPILevel() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取手机Android系统版本号（4.4、5.0、5.1 ...）
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 重启应用(不会关闭进程)
	 * 
	 * @param activity
	 *            当前界面
	 */
	public static void restartApp(Activity activity) {
		if (activity == null) {
			return;
		}
		Intent i = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(i);
		activity.finish();
	}

	/**
	 * 检测是否安装对应的应用程序
	 * 
	 * @param context
	 *            上下文对象
	 * @param packageName
	 *            包名
	 * @return 是否安装对应的应用程序
	 */
	public static boolean checkAppExist(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		try {
			context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * @param ctx
	 *            上下文
	 * @param intent
	 *            intent对象
	 * @return 是否安装app
	 */
	public static boolean isInstalledApp(Context ctx, Intent intent) {
		boolean flag = false;
		List<ResolveInfo> rList = ctx.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER);
		int telAppSize = rList.size();
		if (telAppSize > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 静默卸载App
	 * 
	 * @param pm
	 * @param uninstall
	 * @param packName
	 */
	public static void uninstallApp(String pm, String uninstall, String packName) {
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		@SuppressWarnings("unused")
		String result = "";
		try {
			process = new ProcessBuilder().command(pm, uninstall, packName).start();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			result = new String(baos.toByteArray());
			if (inIs != null)
				inIs.close();
			if (errIs != null)
				errIs.close();
			process.destroy();
		} catch (IOException e) {
			result = e.getMessage();
		}
	}

	/**
	 * 获取当前进程的id
	 * 
	 * @return
	 */
	public static int getProcessId() {
		return android.os.Process.myPid();
	}

	/**
	 * 获取当前App进程的Name
	 * 
	 * @param context
	 * @param processId
	 * @return
	 */
	public static String getProcessName(Context context, int processId) {
		String processName = null;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取所有运行App的进程集合
		List<RunningAppProcessInfo> l = am.getRunningAppProcesses();
		Iterator<RunningAppProcessInfo> i = l.iterator();
		PackageManager pm = context.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == processId) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
			}
		}
		return processName;
	}

	/**
	 * 判断指定进程是否已经打开
	 * 
	 * @param context
	 *            activity界面或者application
	 * @param process
	 *            指定进程 ；
	 * @return true表示已经打开 false表示没有打开
	 */
	public static boolean isAppOpen(Context context, String process) {
		ActivityManager mManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> mRunningApp = mManager.getRunningAppProcesses();
		int size = mRunningApp.size();
		for (int i = 0; i < size; i++) {
			if (process.equals(mRunningApp.get(i).processName)) {
				Log.d(TAG, "找到进程");
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取manifest.xml中Application标签下的配置项
	 * 
	 * @param key
	 *            键名
	 * @return 返回字符串
	 */
	public static int getConfigInt(Context context, String key) {
		int val = 0;
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			val = appInfo.metaData.getInt(key);
		} catch (NameNotFoundException e) {
			Log.e(TAG, e + "");
		}
		return val;
	}

	/**
	 * 读取manifest.xml中Application标签下的配置项，如果不存在，则返回空字符串
	 * 
	 * @param key
	 *            键名
	 * @return 返回字符串
	 */
	public static String getConfigString(Context context, String key) {
		String val = "";
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			val = appInfo.metaData.getString(key);
			if (val == null) {
				Log.e(TAG, "please set config value for " + key + " in manifest.xml first");
			}
		} catch (Exception e) {
			Log.w(TAG, e);
		}
		return val;
	}

	/**
	 * 读取manifest.xml中Application标签下的配置项
	 * 
	 * @param key
	 *            键名
	 * @return 返回字符串
	 */
	public static boolean getConfigBoolean(Context context, String key) {
		boolean val = false;
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			val = appInfo.metaData.getBoolean(key);
		} catch (NameNotFoundException e) {
			Log.e(TAG, e + "");
		}
		return val;
	}

	/**
	 * 获取 string.xml 文件定义的字符串
	 * 
	 * @param resourceId
	 *            资源id
	 * @return 返回 string.xml 文件定义的字符串
	 */
	public static String getString(Context context, int resourceId) {
		Resources res = context.getResources();
		return res.getString(resourceId);
	}

	/**
	 * @param context
	 *            上下文
	 * @param key
	 *            meta data key
	 * @return meta data value
	 */
	public static String getMetaData(Context context, String key) {
		String result = null;
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (applicationInfo != null) {
				String appSign = applicationInfo.metaData.getString(key);
				if (appSign != null) {
					result = appSign;
				}
			}
		} catch (Exception ex) {
			Log.e(TAG, "读取app meta data key 异常.");
		}
		return result;
	}

}
