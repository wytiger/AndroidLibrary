package com.wytiger.mytest;


import com.wytiger.lib.utils.WakeLockUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;

public class WakeLockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wake_lock);

		WakeLockUtil.acquireWakeLock(this, PowerManager.PARTIAL_WAKE_LOCK);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WakeLockUtil.releaseWakeLock();
	}
}
