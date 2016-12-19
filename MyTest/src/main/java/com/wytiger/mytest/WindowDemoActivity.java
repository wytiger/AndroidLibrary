package com.wytiger.mytest;

import com.wytiger.mytest.utils.Tags;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import android.widget.TextView;

public class WindowDemoActivity extends Activity implements OnClickListener {
	private static String TAG = Tags.TAG_WINDOW;

	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private TextView textView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_demo);
		Log.i(TAG, "onCreate");

		initViewAndWindow();

		findViewById(R.id.btnShowWindow).setOnClickListener(this);
		findViewById(R.id.btnHideWindow).setOnClickListener(this);
	}

	private void initViewAndWindow() {
		textView = new TextView(this);
		textView.setBackgroundColor(Color.RED);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(Color.GREEN);
		textView.setText("我是窗口");

		
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		layoutParams = new WindowManager.LayoutParams();
		layoutParams.alpha = (float) 0.5;
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.width = 300;
		layoutParams.height = 300;
		layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
		layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	}

	private void showWindow(TextView textView) {
		Log.i(TAG, "showWindow");
		windowManager.addView(textView, layoutParams);

	}

	private void hideWindow(TextView textView) {
		Log.i(TAG, "hideWindow");

		windowManager.removeView(textView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShowWindow:
			showWindow(textView);
			break;
		case R.id.btnHideWindow:
			hideWindow(textView);
			break;

		default:
			break;
		}
	}

}
