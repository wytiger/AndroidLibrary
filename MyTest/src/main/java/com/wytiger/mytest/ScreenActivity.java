package com.wytiger.mytest;


import com.wytiger.lib.utils.common.ScreenUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ScreenActivity extends Activity {

	private static final String TAG = "ScreenActivity";
	private TextView tvScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 ScreenUtil.fullScreen(this);
//		 ScreenUtil.noTitle(this);
//		 ScreenUtil.hideNavigation(this);
//		
		setContentView(R.layout.activity_screen);
		tvScreen = (TextView) findViewById(R.id.tvScreen);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			StringBuilder sb = new StringBuilder();

			sb.append("getRealMetrics: \n"+ ScreenUtil.getRealMetrics(this));
			sb.append("\ngetRealSize: \n"+ ScreenUtil.getRealSize(this));
			sb.append("\nTotal Area: \n\tWidth: " + ScreenUtil.getRealWidth(this) + ";\tHeight: " + ScreenUtil.getRealHeight(this));
			sb.append("\nScreen Area: \n\tWidth: " + ScreenUtil.getScreenWidth(this) + ";\tHeight: " + ScreenUtil.getScreenHeight(this));
			sb.append("\nApp Area: \n\tWidth: " + ScreenUtil.getAppWidth(this) + ";\tHeight: " + ScreenUtil.getAppHeight(this));
			sb.append("\nContent Area: \n\tWidth: " + ScreenUtil.getContentWidth(this) + ";\tHeight: " + ScreenUtil.getContentHeight(this));
			sb.append("\n");
			sb.append("\nStatusHeight: " + ScreenUtil.getStatusHeight(this));
			sb.append("\nTitleHeight: " + ScreenUtil.getTitleHeight(this));
			sb.append("\nNavigationHeight: " + ScreenUtil.getNavigationHeight(this));

			Log.i(TAG, sb.toString());

			tvScreen.setText(sb.toString());
		}
	}
}
