package com.wytiger.lib.utils;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class DeviceUtil {
	private static final String TAG = "DeviceUtil";
	private static final String DEVICE_ID = "Unknow";

	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param phone
	 */
	public static void dialPhone(Context context, int phone) {
		try {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 播放声音
	 * @param context
	 * @param soundId
	 * @param release  播放完成是否要释放资源
	 */
	public static void playSound(Context context, int soundId, final boolean release) {
		final MediaPlayer mp = MediaPlayer.create(context, soundId);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer player) {
				if (release) {					
					player.release();
				}
			}
		});
	}
	
	/**
	 * 打开系统相机
	 * 
	 * @param context
	 * @param oritation
	 *            1:后置back, 2:前置front
	 */
	public static void openCamera(Context context, int oritation) {
		// 调用android自带的照相机
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		context.startActivity(intent);
	}

	/**
	 * 调节系统音量
	 * 
	 * @param context
	 *            上下文
	 * @param volume
	 *            音量大小
	 * @param flags
	 *            调节呈现方式 ： FLAG_PLAY_SOUND, 调整音量时播放声音; FLAG_SHOW_UI
	 *            调整时显示音量条,就是按音量键出现的那个, 0 表示什么也没有
	 */
	public static void setVolume(Context context, int volume, int flags) {
		try {
			AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			am.setStreamVolume(AudioManager.STREAM_RING, volume, flags);
			am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, flags);
		} catch (Exception e) {
			Log.e("音量改变失败 ", e.getMessage());
		}

	}

	/**
	 * 改变屏幕亮度
	 * 
	 * @param activity
	 * @param brightess
	 *            亮度, 0-255
	 */
	public static void setScreenBrightness(Context context, int brightess) {

		try {
			// 先关闭自动亮度
			android.provider.Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			// 调节亮度 255最亮
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightess);
		} catch (Exception e) {
			Log.e("屏幕亮度改变失败", e.getMessage());
		}
	}

	/**
	 * 设置熄屏时间
	 * 
	 * @param context
	 *            上下文
	 * @param second
	 *            熄屏时间，单位秒
	 */
	public static void setScreenOffTime(Context context, int second) {
		Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, second * 1000);
	}

	/**
	 * @return 获得手机设备id, deviceId
	 */
	public static String getDeviceId(Context context) {
		String deviceId;
		deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (TextUtils.isEmpty(deviceId)) {
			deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		}
		return deviceId;
	}

	/**
	 * 获取设备IMEI串
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * Returns the unique device ID, for example, the IMEI for GSM and the MEID
	 * or ESN for CDMA phones.
	 * 
	 * @return 获得手机端终端标识
	 */
	public static String getTerminalSign(Context context) {
		String tvDevice;
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		tvDevice = tm.getDeviceId();
		if (tvDevice == null) {
			tvDevice = getLocalMacAddress(context);
		}

		if (tvDevice == null) {
			tvDevice = DEVICE_ID;
		}

		Log.d(TAG, "唯一终端标识号：" + tvDevice);
		return tvDevice;
	}

	/**
	 * 申请管理员权限，会弹出激活界面
	 * 
	 * @param context
	 */
	public static void acquireAdmin(Context context) {
		DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
		if (!policyManager.isAdminActive(componentName)) {
			Intent manager = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			manager.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
			manager.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(manager);
		}
	}

	/**
	 * 移除管理员权限
	 * 
	 * @param context
	 */
	public static void removeAdmin(Context context) {
		DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
		policyManager.removeActiveAdmin(componentName);
	}

	public class MyAdminReceiver extends DeviceAdminReceiver {
		/**
		 * 禁用
		 */
		@Override
		public void onDisabled(Context context, Intent intent) {
			super.onDisabled(context, intent);
		}

		/**
		 * 激活
		 */
		@Override
		public void onEnabled(Context context, Intent intent) {
			super.onEnabled(context, intent);
		}

		@Override
		public DevicePolicyManager getManager(Context context) {
			return super.getManager(context);
		}

		@Override
		public ComponentName getWho(Context context) {
			return super.getWho(context);
		}

		@Override
		public CharSequence onDisableRequested(Context context, Intent intent) {
			return super.onDisableRequested(context, intent);
		}

		@Override
		public void onPasswordExpiring(Context context, Intent intent) {
			super.onPasswordExpiring(context, intent);
		}

		@Override
		public void onPasswordFailed(Context context, Intent intent) {
			super.onPasswordFailed(context, intent);
		}

		@Override
		public void onPasswordSucceeded(Context context, Intent intent) {
			super.onPasswordSucceeded(context, intent);
		}

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			super.onReceive(arg0, arg1);
		}
	}

	/**
	 * 获取MAC地址
	 * 
	 * @return 返回MAC地址
	 */
	private static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info == null) {
			return "";
		}
		return info.getMacAddress();
	}

}
