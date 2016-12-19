package com.wytiger.mytest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class ZoomActivity2 extends Activity implements OnClickListener {
	private FrameLayout frameLayout;
	private SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 开启硬件加速
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		setContentView(R.layout.activity_zoom2);

		initView();
	}

	private void initView() {
		frameLayout = (FrameLayout) findViewById(R.id.flContainer);
		surfaceView = new SurfaceView(this);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(200, 200);
		// layoutParams.gravity = Gravity.CENTER;
		frameLayout.addView(surfaceView, layoutParams);
		// surfaceView.setLayoutParams(layoutParams);
		// frameLayout.addView(surfaceView);

		// surfaceView.setZOrderMediaOverlay(true);
		// surfaceView.setZOrderOnTop(false);

		ImageView imageView = (ImageView) findViewById(R.id.ivClick);
		imageView.bringToFront();

		imageView.setOnClickListener(this);
		findViewById(R.id.btnZoom).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnZoom:
			// zoomByAnimation(frameLayout);
			zoomByAnimator(surfaceView);
			break;
		case R.id.ivClick:
			Toast.makeText(ZoomActivity2.this, "点击图片", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	protected void zoomByAnimation(View view) {
		Log.i("zoom", "zoomByAnimation");

		Animation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		view.startAnimation(animation);
		// view.startAnimation(animation);

		// view.setAnimation(animation);
		// animation.start();
	}

	protected void zoomByAnimator(View view) {
		Log.i("zoom", "zoomByAnimator");

		Animator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
		Animator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
		animatorSet.setDuration(1000);
		animatorSet.start();
	}
}
