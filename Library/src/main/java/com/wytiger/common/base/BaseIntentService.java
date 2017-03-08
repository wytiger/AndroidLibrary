package com.wytiger.common.base;

import com.wytiger.common.manager.AppActivityManager;

import android.app.IntentService;
import android.content.Intent;


/**
 * 基本的IntentService: onHandleIntent方法在子线程执行,并且任务完成后服务就销毁
 * @author wytiger
 * @date 2016年2月15日
 */
public abstract class BaseIntentService extends IntentService {

	public BaseIntentService(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AppActivityManager.addService(this);
	}

	
	/**
	 * 在子线程执行
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		
	}
	
	
	/**
	 * 任务完成后服务销毁
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		AppActivityManager.removeService(this);
	}
}
