package com.wytiger.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wytiger.mytest.utils.Tags;

public class MainActivity extends Activity implements OnClickListener {
	private static String TAG = Tags.TAG_TEST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btnGrayUnckickable).setOnClickListener(this);
		findViewById(R.id.btnWindow).setOnClickListener(this);
		findViewById(R.id.btnSurfaceZoom).setOnClickListener(this);
		findViewById(R.id.btnZoom).setOnClickListener(this);
		findViewById(R.id.btnCheckPermission).setOnClickListener(this);
		findViewById(R.id.btnHeartBeat).setOnClickListener(this);
		findViewById(R.id.btnSystemUI).setOnClickListener(this);
		findViewById(R.id.btnScreen).setOnClickListener(this);


		//迁移到android studio and git
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGrayUnckickable:
			startActivity(new Intent(MainActivity.this, UnclickGrayActivity.class));
			break;
			
		case R.id.btnWindow:
			startActivity(new Intent(MainActivity.this, WindowDemoActivity.class));
			break;
			
		case R.id.btnSurfaceZoom:
			startActivity(new Intent(MainActivity.this, SurfaceZoomActivity.class));
			break;

		case R.id.btnZoom:
			startActivity(new Intent(MainActivity.this, ZoomActivity.class));
			break;

		case R.id.btnCheckPermission:
			startActivity(new Intent(MainActivity.this, CheckPermissionActivity.class));
			break;
			
		case R.id.btnHeartBeat:
			startActivity(new Intent(MainActivity.this, HeartBeatActivity.class));
			break;
			
		case R.id.btnSystemUI:
			startActivity(new Intent(MainActivity.this, SystemUIActivity.class));
			break;
			
		case R.id.btnScreen:
			startActivity(new Intent(this, ScreenActivity.class));
			break;
			
		default:
			break;
		}

	}

}
