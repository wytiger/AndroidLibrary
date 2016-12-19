package com.wytiger.mytest;

import com.wytiger.lib.widget.ViewWrapper;
import com.wytiger.mytest.utils.Tags;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

public class ZoomActivity extends Activity {
	int w;
	int h;

	private FrameLayout frameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 开启硬件加速
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom);

		initView();
	}

	private void initView() {
		frameLayout = (FrameLayout) findViewById(R.id.flContainer);

		findViewById(R.id.btnZoom).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zoom();
			}

		});

		frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				w = frameLayout.getMeasuredWidth();
				h = frameLayout.getMeasuredHeight();
				Log.i(Tags.TAG_TEST, "w = " + w + ", h= " + h);

			}
		});
	}

	private void zoom() {
		Log.i(Tags.TAG_TEST, "Zoom");
		// frameLayout.setX(x)

		// 无法设置缩放中心
		// Animator animator1 = ObjectAnimator.ofFloat(frameLayout, "scaleX",
		// (float)1.0, (float)0.5);
		// Animator animator2 = ObjectAnimator.ofFloat(frameLayout, "scaleY",
		// (float)1.0, (float)0.5);
		// 宽高,没有set方法，不起作用
		// Animator animator1 = ObjectAnimator.ofFloat(frameLayout, "width",
		// (float)1.0, (float)0.5);
		// Animator animator2 = ObjectAnimator.ofFloat(frameLayout, "heigh",
		// (float)1.0, (float)0.5);

		// 本质是平移
		// Animator animator1 = ObjectAnimator.ofFloat(frameLayout, "X", 300);
		// Animator animator2 = ObjectAnimator.ofFloat(frameLayout, "Y", 500);

		// Animator animator1 = ObjectAnimator.ofFloat(frameLayout, "X", -300);
		// Animator animator2 = ObjectAnimator.ofFloat(frameLayout, "Y", -500);

		SurfaceView surfaceView = new SurfaceView(this);
		LayoutParams layoutParams = new LayoutParams(500, 500);
		frameLayout.addView(surfaceView, layoutParams);

		ViewWrapper viewWrapper = new ViewWrapper(frameLayout);
		Animator animator1 = ObjectAnimator.ofInt(viewWrapper, "width", w, 300);
		Animator animator2 = ObjectAnimator.ofInt(viewWrapper, "height", h, 500);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(1000);
		animatorSet.playTogether(animator1, animator2);
		animatorSet.start();

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
