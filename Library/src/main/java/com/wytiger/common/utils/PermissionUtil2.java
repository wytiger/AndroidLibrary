package com.wytiger.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Build;
import android.util.Log;

/**
 * 权限工具类,主要用于检测是否有指定的具体的权限.
 * 解决小米魅族等单独的权限机制
 *
 * @author wytiger
 * @date 2016-8-15
 */
public class PermissionUtil2 {
	public static final String TAG = "TAG_PERMISSION";

	public static final int STATE_NO_PERMISSION = -1;
	public static final int STATE_RECORDING = 0;
	public static final int STATE_SUCCESS = 1;
	

	// op flag
	public static final int opCameraFlag = 26;
	public static final int opRecordAudioFlag = 27;


	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * 针对API19以上
	 * 返回 0 代表有权限，1代表没有权限，-1函数出错啦
	 * 
	 * @param context
	 * @param op
	 * @return
	 */
	private static int checkOp(Context context, int op) {
		if (Build.VERSION.SDK_INT >= 19) {
			try {
				AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
				Class clazz = AppOpsManager.class;
				Class[] cArg = new Class[3];
				cArg[0] = int.class;
				cArg[1] = int.class;
				cArg[2] = String.class;
				Method checkOpMethod = clazz.getDeclaredMethod("checkOp", cArg);
				return (Integer) checkOpMethod.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 是否有指定权限，在api19以上有效
	 * @param mContext：上下文
	 * @param opFlag: 指定操作码，see AppOpsManager define
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public boolean hasPermission(Context mContext, int opFlag) {
		boolean hasPermission = false;

		if (Build.VERSION.SDK_INT >= 19) {
			if (checkOp(mContext, opFlag) == 0) {
				hasPermission = true;

			} else if (checkOp(mContext, opFlag) == 1) {
				hasPermission = false;

			} else {
				hasPermission = false;
				Log.e(TAG, "内部出错");
			}

			return hasPermission;

		} else {
			Log.e(TAG, "API below 19 cannot invoke!");
		}
		return true;
	}

	
	/**
	 * 获取录音状态
	 * 
	 * @return
	 */
	public static int getRecordState() {
		int minBuffer = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
		short[] point = new short[minBuffer];
		int readSize = 0;

		AudioRecord audioRecord = null;
		try {
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
					(minBuffer * 100));
			// 开始录音
			audioRecord.startRecording();// 检测是否可以进入初始化状态

		} catch (Exception e) {
			Log.e(TAG, "catch, 捕捉到异常, 无录音权限, e = " + e.getMessage());

			if (audioRecord != null) {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
				Log.i(TAG, "catch, 返回对象非空,释放资源");
			} else {
				Log.i(TAG, "catch, 返回对象非空");
			}

			return STATE_NO_PERMISSION;
		}

		// 检测是否在录音中
		if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
			// 6.0以下机型都会返回此状态，故使用时需要判断bulid版本
			if (audioRecord != null) {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
				Log.e(TAG, "无法启动录音, 无录音权限");
			}
			return STATE_RECORDING;

		} else {// 正在录音
			readSize = audioRecord.read(point, 0, point.length);

			// 检测是否可以获取录音结果
			if (readSize <= 0) {
				if (audioRecord != null) {
					audioRecord.stop();
					audioRecord.release();
					audioRecord = null;
				}
				Log.e(TAG, "没有获取到录音数据，无录音权限");
				return STATE_NO_PERMISSION;

			} else {
				if (audioRecord != null) {
					audioRecord.stop();
					audioRecord.release();
					audioRecord = null;
				}
				Log.i(TAG, "获取到录音数据, 有录音权限");
				return STATE_SUCCESS;
			}
		}
	}
	
	
	//--------------------------------分割线--------------------------------------
	
	
	/**
	 * 判断是否有录音权限
	 * 
	 * @return
	 */
	public static boolean hasRecordPermission() {
		boolean hasPermission = false;
		if (getRecordState() == STATE_SUCCESS) {
			hasPermission = true;
		} else {
			hasPermission = false;
		}

		return hasPermission;
	}

	
	/**
	 * 是否有摄像头权限
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean hasCameraPermission() {
		// 无权限可能出现的情况（部分）：
		// 1, 直接抛异常
		// 2, 不抛异常,返回对象为空
		// 3, 获取到的数据为空

		boolean canUse = true;
		Camera mCamera = null;
		try {
			mCamera = Camera.open();
			mCamera.setParameters(mCamera.getParameters());
		} catch (Exception e) {
			Log.e(TAG, "catch, 捕捉到异常, 无摄像头权限");
			// 1, 直接抛异常
			canUse = false;

			if (mCamera != null) {// 返回对象非空(魅族)
				mCamera.release();				
				mCamera = null;
				Log.i(TAG, "catch, 对象非空,释放资源");

			} else if (mCamera == null) {// 2, 返回对象为空
				Log.i(TAG, "catch, 对象为空");
			}

		} finally {
			if (mCamera != null) {
				canUse = true;
				mCamera.release();
				mCamera = null;
				Log.i(TAG, "finally, 对象非空,释放资源");

			} else if (mCamera == null) {// 2, 返回对象为空
				canUse = false;
				Log.i(TAG, "finally, 对象为空");
			}
		}

		Log.i(TAG, "返回");
		return canUse;
	}

}
