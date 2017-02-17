package com.wytiger.common.utils;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * WiFiUtil
 * 
 * @author wytiger
 * @date 2016-8-11
 */
public class WiFiUtil {
	private static WiFiUtil instance;
	private WifiManager wifiManager;

	// wifi锁, 保持wifi活跃不休眠
	private WifiManager.WifiLock wifiLock;
	// 当前连接的wifi信息
	private WifiInfo activeWifiInfo;

	// 扫描到的wifi列表
	private List<ScanResult> wifiList;
	// 配置好的WiFi列表
	private List<WifiConfiguration> wifiConfigs;
	

	private WiFiUtil(Context context) {
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		activeWifiInfo = wifiManager.getConnectionInfo();// 获得连接的WiFi信息
	}

	public static WiFiUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (WiFiUtil.class) {
				if (instance == null) {
					instance = new WiFiUtil(context);
				}
			}
		}

		return instance;
	}

	/**
	 * 打开WiFi
	 */
	public void openWifi() {
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * 关闭WiFi
	 */
	public void closeWifi() {
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * 扫描WiFi热点, 并将结果添加到列表
	 */
	public void scanWifi() {
		// 开始扫描
		wifiManager.startScan();

		// 获得WiFi列表
		wifiList = wifiManager.getScanResults();

		// 获得配置好的WiFi列表
		wifiConfigs = wifiManager.getConfiguredNetworks();
	}

	/**
	 * 连接配置好的指定id的网络
	 * 
	 * @param index
	 *            配置好的网络列表对应的索引
	 */
	public void connectWifi(int index) {
		// 索引大于配置好的网络索引则返回
		if (index > wifiConfigs.size()) {
			return;
		}

		int networkId = wifiConfigs.get(index).networkId;
		// 连接配置好的网络
		wifiManager.enableNetwork(networkId, true);
	}

	/**
	 * 断开指定ID的网络
	 * 
	 * @param netId
	 */
	public void disConnectionWifi(int netId) {
		wifiManager.disableNetwork(netId);
		wifiManager.disconnect();
	}

	/**
	 * 获得IP地址
	 * 
	 * @return IP地址对应的字符串
	 */
	public String getIPAddress() {
		// 判断是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		int intIp = activeWifiInfo.getIpAddress();

		return intToStringIp(intIp);
	}

	/**
	 * 获取mac地址
	 * 
	 * @return
	 */
	public String getMacAddress() {
		return (activeWifiInfo == null) ? "NULL" : activeWifiInfo.getMacAddress();
	}

	/**
	 * 获得WiFi信号强度
	 * 
	 * @return
	 */
	public int getSignalLevel() {
		// 判断是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		int strength = WifiManager.calculateSignalLevel(activeWifiInfo.getRssi(), 5);

		return strength;
	}

	/**
	 * 获得WiFi状态
	 * 
	 * @return
	 */
	public int getWifiState() {
		return wifiManager.getWifiState();
	}

	// 得到连接的BSSID
	public String getBSSID() {
		return (activeWifiInfo == null) ? "NULL" : activeWifiInfo.getBSSID();
	}

	// 得到连接的ID
	public int getNetWordId() {
		return (activeWifiInfo == null) ? 0 : activeWifiInfo.getNetworkId();
	}

	/**
	 * 将整形IP转化为字符串型IP
	 * 
	 * @param i
	 * @return
	 */
	private String intToStringIp(int intIp) {

		return (intIp & 0xFF) + "." + ((intIp >> 8) & 0xFF) + "." + ((intIp >> 16) & 0xFF) + "." + (intIp >> 24 & 0xFF);
	}

	/**
	 * 通过SSID判断指定WiFi是否已配置好
	 * 
	 * @param ssid
	 * @return 网络id
	 */
	public int isConfiguration(String ssid) {
		for (int i = 0; i < wifiConfigs.size(); i++) {

			if (ssid.equals(wifiConfigs.get(i).SSID)) {

				return wifiConfigs.get(i).networkId;
			}
		}

		return -1;
	}

	/**
	 * 添加WiFi配置
	 * 
	 * @param wifiList
	 * @param ssid
	 * @param pwd
	 * @return
	 */
	public int addWifiConfig(List<ScanResult> wifiList, String ssid, String pwd) {
		int wifiId = -1;

		for (int i = 0; i < wifiList.size(); i++) {
			ScanResult wifi = wifiList.get(i);

			if (wifi.SSID.equals(ssid)) {
				WifiConfiguration wifiConfig = new WifiConfiguration();

				// 配置信息
				wifiConfig.SSID = "\"" + wifi.SSID + "\"";// \"转义字符代表",即代表一个双引号字符
				wifiConfig.preSharedKey = "\"" + pwd + "\"";// WPA-PSK密码
				wifiConfig.hiddenSSID = false;
				wifiConfig.status = WifiConfiguration.Status.ENABLED;

				wifiId = wifiManager.addNetwork(wifiConfig);
				if (wifiId != -1) {
					return wifiId;
				}
			}
		}

		return wifiId;

	}

	/**
	 * 添加一个网络并连接
	 */
	public void addWifi(WifiConfiguration configuration) {
		int wcgId = wifiManager.addNetwork(configuration);
		wifiManager.enableNetwork(wcgId, true);
	}

	/**
	 * 获得WifiManager
	 * 
	 * @return
	 */
	public WifiManager getWifiManager() {

		return wifiManager;
	}

	/**
	 * 获得WiFi热点列表
	 * 
	 * @return
	 */
	public List<ScanResult> getWifiList() {

		return wifiList;
	}

	/**
	 * 获得配置好的WiFi列表
	 * 
	 * @return
	 */
	public List<WifiConfiguration> getConfigWifi() {
		return wifiConfigs;
	}

	public WifiInfo getActiveWifiInfo() {

		return activeWifiInfo;
	}

	/**
	 * @param lockType
	 *            WIFI_MODE_FULL,扫描，自动的尝试去连接一个曾经配置过的点;
	 *            WIFI_MODE_SCAN_ONLY,只剩下扫描;
	 *            WIFI_MODE_FULL_HIGH_PERF,在第一种模式的基础上，保持最佳性能
	 * 
	 * @param lockName
	 *            锁的名称
	 * @return wifiLock
	 */
	public void acquireLock(int lockType) {
		this.wifiLock = wifiManager.createWifiLock(lockType, "WiFiUtil");
		wifiLock.acquire();
	}

	/**
	 * 解锁WifiLock
	 */
	public void releaseLock() {
		// 判断是否锁定
		if (wifiLock.isHeld()) {
			wifiLock.acquire();
		}
	}

	
	private int wifiSleepPolicy;
	/**
	 * 通过修改系统wifi设置,使wifi保持活跃 注意添加权限：<uses-permission
	 * Android:name="android.permission.WRITE_SETTINGS"/>
	 * 
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public void keepWifiAlive(Context context) {
		wifiSleepPolicy = Settings.System.getInt(context.getContentResolver(), 
				android.provider.Settings.System.WIFI_SLEEP_POLICY,
				Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
		Settings.System.putInt(context.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_NEVER);
	}

	/**
	 * 恢复wifi休眠策略
	 * @param context
	 */
	public void stopWifiAlive(Context context) {
		
		Settings.System.putInt(context.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY, wifiSleepPolicy);
	}
	
	
	/**
	 * 打开系统WiFi设置
	 */
	public void openWifiSettings(Context context) {
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
