package com.wytiger.mytest;

import com.wytiger.mytest.utils.Tags;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class UnclickGrayActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gray_unclickable);

		imageView = (ImageView) findViewById(R.id.imageView1);
		findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(Tags.TAG_TEST, "Clickable");
				imageView.setClickable(true);
//				imageView.setEnabled(true);
			}
		});
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(Tags.TAG_TEST, "UnClickable");
				imageView.setClickable(false);
//				imageView.setEnabled(false);

			}
		});
	}
}
