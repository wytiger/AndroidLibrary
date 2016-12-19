package com.wytiger.lib.base;

import com.wytiger.lib.manager.AppManager;

import android.app.Service;

public abstract class BaseService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();		
		AppManager.addService(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();		
		AppManager.removeService(this);
	}
}
