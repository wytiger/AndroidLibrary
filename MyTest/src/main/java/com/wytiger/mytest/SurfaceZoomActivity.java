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
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class SurfaceZoomActivity extends Activity implements OnClickListener {
	private static String TAG = Tags.TAG_TEST;

	private int w;
	private int h;

	private FrameLayout frameLayout;
	private SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surface_zoom);

		initView();
	}

	private void initView() {
		frameLayout = (FrameLayout) findViewById(R.id.flContainer);
		surfaceView = new SurfaceView(this);
		LayoutParams layoutParams = new LayoutParams(200, 200);
		frameLayout.addView(surfaceView, layoutParams);

		frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				w = frameLayout.getMeasuredWidth();
				h = frameLayout.getMeasuredHeight();
				Log.i(TAG, "w = " + w + ", h= " + h);

			}
		});

		// surfaceView.setZOrderMediaOverlay(true);
		// surfaceView.setZOrderOnTop(false);

		ImageView imageView = (ImageView) findViewById(R.id.ivClick);
		imageView.setOnClickListener(this);
		imageView.bringToFront();

		findViewById(R.id.btnZoomByAnimation).setOnClickListener(this);
		findViewById(R.id.btnZoomByAnimator).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnZoomByAnimation:
			zoomByAnimation();
			break;
			
		case R.id.btnZoomByAnimator:
			zoomByAnimator(frameLayout);
			break;
			
		case R.id.ivClick:
			Toast.makeText(SurfaceZoomActivity.this, "点击图片", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	protected void zoomByAnimation() {
		Log.i(TAG, "zoomByAnimation");
		Animation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		frameLayout.startAnimation(animation);
	}

	private void zoomByAnimator(View view) {
		Log.i(TAG, "zoomByAnimator");

		ViewWrapper viewWrapper = new ViewWrapper(view);
		Animator animator1 = ObjectAnimator.ofInt(viewWrapper, "width", w, 400);
		Animator animator2 = ObjectAnimator.ofInt(viewWrapper, "height", h, 400);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(1000);
		animatorSet.playTogether(animator1, animator2);
		animatorSet.start();
	}
}
