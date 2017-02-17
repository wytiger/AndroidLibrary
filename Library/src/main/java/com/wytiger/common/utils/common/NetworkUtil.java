package com.wytiger.common.utils.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 判断网络状态. 注意：需要相关网络权限
 */
public class NetworkUtil {
	/** 网络类型未知 */
	public static final int IS_UNKNOW = -1;

	/** 网络不可用 */
	public static final int IS_NO_NETWORK = 0;
	/** 是WiFi连接 */
	public static final int IS_WIFI = 1;
	/** 是Mobile数据连接 */
	public static final int IS_MOBILE = 2;

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAvalible(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null || !ni.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 判断是否是Mobile数据连接
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
	}

	public static boolean is4G(Context context) {
		boolean is4G = false;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			int netWorkType = telephonyManager.getNetworkType();
			if (netWorkType == TelephonyManager.NETWORK_TYPE_LTE) {
				is4G = true;
			}
		} catch (Exception e) {
			is4G = false;
		}

		return is4G;
	}

	public static boolean is3G(Context context) {
		boolean is3G = false;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			int netWorkType = telephonyManager.getNetworkType();
			if (netWorkType == TelephonyManager.NETWORK_TYPE_UMTS || netWorkType == TelephonyManager.NETWORK_TYPE_HSDPA
					|| netWorkType == TelephonyManager.NETWORK_TYPE_HSUPA || netWorkType == TelephonyManager.NETWORK_TYPE_HSPA
					|| netWorkType == TelephonyManager.NETWORK_TYPE_EVDO_0 || netWorkType == TelephonyManager.NETWORK_TYPE_EVDO_A
					|| netWorkType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
				is3G = true;
			}
		} catch (Exception e) {
			is3G = false;
		}

		return is3G;
	}

	public static boolean is2G(Context context) {
		boolean is2G = false;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			int netWorkType = telephonyManager.getNetworkType();
			if (netWorkType == TelephonyManager.NETWORK_TYPE_GPRS || netWorkType == TelephonyManager.NETWORK_TYPE_EDGE
					|| netWorkType == TelephonyManager.NETWORK_TYPE_CDMA || netWorkType == TelephonyManager.NETWORK_TYPE_1xRTT) {
				is2G = true;
			}
		} catch (Exception e) {
			is2G = false;
		}

		return is2G;
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (!isAvalible(context)) {

			return NetworkUtil.IS_NO_NETWORK;
		} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {

			return NetworkUtil.IS_WIFI;
		} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()) {

			return NetworkUtil.IS_MOBILE;
		} else {

			return NetworkUtil.IS_UNKNOW;
		}
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Context context) {
		Intent intent = null;
		// 判断手机系统的版本 即API大于10 就是3.0或以上版本
		if (android.os.Build.VERSION.SDK_INT > 10) {
			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction("android.intent.action.VIEW");
		}
		context.startActivity(intent);
	}

}
