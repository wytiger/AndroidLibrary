package com.wytiger.lib.gloable;

import android.app.Application;
import android.util.Log;

/**
 * 全局应用程序App
 * 
 * @author wytiger
 * @date 2016年2月14日
 */
public class GloableApp extends Application {
	private static final String TAG = GloableApp.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	/**
	 * never be called on s real device
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
