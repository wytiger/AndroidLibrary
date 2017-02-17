package com.wytiger.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Google Android权限权限管理工具类
 *
 * @author wytiger
 * @date 2016-8-15
 */
public class PermissionUtil {

	/**
	 * 是否有权限
	 * 
	 * @param context
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(Context context, String permission) {
		int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, permission);
		if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 申请权限
	 * 
	 * @param activity
	 * @param permission
	 * @param requestCode
	 */
	public static void requestPermission(Activity activity, String permission, int requestCode) {
		ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
	}

	/**
	 * 申请权限
	 * 
	 * @param activity
	 * @param permissions
	 * @param requestCode
	 */
	public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
		ActivityCompat.requestPermissions(activity, permissions, requestCode);
	}

	/**
	 * 申请权限
	 * 
	 * @param activity
	 * @param permissions
	 * @param requestCode
	 */
	@TargetApi(23)
	public static void requestPermission(Fragment fragment, String[] permissions, int requestCode) {
		fragment.requestPermissions(permissions, requestCode);
	}

	/**
	 * 是否需要向用户解释为什么需要这个权限.
	 * 
	 * shouldShowRequestPermissionRationale()返回值的说明：
	 * 
	 * 1. 第一次请求权限时，用户拒绝了，下一次则返回 true,应该显示一些为什么需要这个权限的说明;
	 * 
	 * 2. 第二次请求权限时，用户拒绝了，并选择了"不在提醒"的选项时则返回 false.
	 * 
	 * 3. 设备的策略禁止当前应用获取这个权限的授权则返回false
	 */
	@TargetApi(23)
	public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {

		return activity.shouldShowRequestPermissionRationale(permission);
	}

}
