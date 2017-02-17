package com.wytiger.common.utils.special;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

@SuppressLint({ "Wakelock", "NewApi" })
@SuppressWarnings("deprecation")
public class LockScreenUtil {
	private static final String TAG = "AppUtil";

	private static LockScreenUtil lockScreenUtil;

	private PowerManager pm = null;
	private WakeLock wl = null;
	private KeyguardManager km = null;
	private KeyguardLock kl = null;

	private LockScreenUtil(Context context) {
		if (pm == null) {
			pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		}
		if (wl == null) {
			wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, TAG);
		}
		if (km == null) {
			km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

		}
		if (kl == null) {
			kl = km.newKeyguardLock(TAG);
		}
	}

	public static LockScreenUtil getInstance(Context context) {
		if (lockScreenUtil == null) {
			lockScreenUtil = new LockScreenUtil(context);
		}

		return lockScreenUtil;

	}

	/**
	 * 唤醒屏幕
	 * 
	 * @param context
	 * @param flag
	 */
	@SuppressLint("Wakelock")
	public void wakeScreen(boolean flag) {
		if (flag) {
			wl.acquire();// 申请锁
			kl.reenableKeyguard();

		} else {
			// wl.acquire();
			wl.release();
		}

	}

	/**
	 * 解屏
	 * 
	 * @param contxet
	 * @param flag
	 */
	@SuppressLint("NewApi")
	public void unLockScreen(boolean flag) {

		if (flag) {
			if (km.isKeyguardSecure()) {
			}
			
			if (km.isKeyguardLocked()) {
				wl.release(); // 释放锁
				kl.disableKeyguard();
			}
		} else {
			kl.reenableKeyguard();
		}
	}

	public void wakeUnlock() {
		// wl.release(); // 释放锁

		if (km.isKeyguardLocked()) {
			Log.i("test", "===========锁屏中================");
		}

		kl.disableKeyguard();
	}

	@SuppressLint("NewApi")
	public void wake2UnlockScreen(boolean flag) {
		if (flag) {

			kl.reenableKeyguard();
			if (!wl.isHeld())
				wl.acquire();// 申请锁
			if (km.isKeyguardSecure()) {
				Log.i("test", "=== 锁屏有密码 ===");
			}
			if (wl.isHeld())
				wl.release(); // 释放锁
			kl.disableKeyguard();

		} else {
			wl.release();
			kl.reenableKeyguard();
		}
	}
}
