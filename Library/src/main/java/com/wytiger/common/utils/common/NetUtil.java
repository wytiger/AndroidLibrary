package com.wytiger.common.utils.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 判断网络状态. 注意：需要相关网络权限
 */
public class NetUtil {
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
	 * 获取网络类型
	 *
	 * @param context
	 * @return
	 */
	public static int getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (!isAvalible(context)) {

			return NetUtil.IS_NO_NETWORK;
		} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {

			return NetUtil.IS_WIFI;
		} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()) {

			return NetUtil.IS_MOBILE;
		} else {

			return NetUtil.IS_UNKNOW;
		}
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
	 * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
	 * 延时非常高，不可用
	 *
	 * @return
	 */
	public static boolean ping() {
		//return true;
		String result = null;
		try {
			// ping 的地址，可以换成任何一种可靠的外网
			String ip = "www.baidu.com";
			//ping -c 1 -w 3  中  ，-c 是指ping的次数 1是指ping 1次 ，-w 3  以秒为单位指定超时间隔，是指超时时间为3秒
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 3 " + ip);
			// 读取ping的内容，可以不加
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while ((content = in.readLine()) != null) {
				stringBuffer.append(content);
			}
			LogUtil.d("------ping-----", "result content : " + stringBuffer.toString());
			// ping的状态
			int status = p.waitFor();
			if (status == 0) {
				result = "success";
				return true;
			} else {
				result = "failed";
			}
		} catch (IOException e) {
			result = "ping IOException";
		} catch (InterruptedException e) {
			result = "ping InterruptedException";
		} finally {
			LogUtil.d("ping", "result = " + result);
		}
		return false;
	}

	/**
	 * 获取ip地址
	 *
	 * @return
	 */
	public static String getIPAddress() {
		String hostIp = "";
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			InetAddress ia = null;
			while (enumeration.hasMoreElements()) {
				NetworkInterface ni =  enumeration.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					ia = ias.nextElement();
					if (ia instanceof Inet6Address) {
						continue;// skip ipv6
					}
					String ip = ia.getHostAddress();
					if (!"127.0.0.1".equals(ip)) {
						hostIp = ia.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return hostIp;
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
