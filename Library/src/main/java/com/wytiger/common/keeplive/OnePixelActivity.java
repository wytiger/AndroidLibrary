package com.wytiger.common.keeplive;

import android.app.Activity;
import android.os.Bundle;

import com.wytiger.common.utils.common.LogUtil;

/**
 * 透明1像素activity，在清单文件注册
 * 
 * @author wytiger
 * @date 2016-8-16
 */
public class OnePixelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i(KeepLiveManager.TAG, "OnePixelActivity onCreate");

		KeepLiveManager.getInstance().initOnePixelActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.i(KeepLiveManager.TAG, "OnePixelActivity onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.i(KeepLiveManager.TAG, "OnePixelActivity onPause");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.i(KeepLiveManager.TAG, "OnePixelActivity onDestroy");
	}

}
