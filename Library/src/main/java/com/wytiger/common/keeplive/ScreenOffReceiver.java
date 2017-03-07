package com.wytiger.common.keeplive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wytiger.common.utils.common.LogUtil;


/**
 * 在清单文件注册
 *
 * @author wytiger
 * @date 2016-8-18
 */
public class ScreenOffReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		LogUtil.i(KeepLiveManager.TAG, "ScreenOnOffReceiver onReceive, action = " + action);
		
		if (action.equals(Intent.ACTION_SCREEN_OFF) || action.equals(Intent.ACTION_USER_BACKGROUND)) {
			KeepLiveManager.getInstance().startOnePixelActivity(context);
			
		} else if (action.equals(Intent.ACTION_SCREEN_ON) || action.equals(Intent.ACTION_USER_FOREGROUND)) {
			
			KeepLiveManager.getInstance().finishOnePixelActivity();
		}
	}

}
