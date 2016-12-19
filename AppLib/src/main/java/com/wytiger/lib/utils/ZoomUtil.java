package com.wytiger.lib.utils;

import com.wytiger.lib.widget.ViewWrapper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 缩放工具类
 * 
 * @author wytiger
 * @date 2016-7-22
 */
public class ZoomUtil {
	protected static final String TAG = "ZoomUtil";

	public static void zoomByObjectAnimator(View view, long duration, int beforeWidth, int afterWidth, int beforeHeigth, int afterHeigth) {
		Log.i(TAG, "zoomByObjectAnimator");

		ViewWrapper viewWrapper = new ViewWrapper(view);
		Animator animator1 = ObjectAnimator.ofInt(viewWrapper, "width", beforeWidth, afterWidth);
		Animator animator2 = ObjectAnimator.ofInt(viewWrapper, "height", beforeHeigth, afterHeigth);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(duration);
		animatorSet.setInterpolator(new LinearInterpolator());
		animatorSet.playTogether(animator1, animator2);
		animatorSet.start();
	}

	public static void zoomByValueAnimator(final View view, long duration, final float scaleRatio) {
		Log.i(TAG, "zoomByValueAnimator");
		final int oriWidth = view.getMeasuredWidth();
		final int oriHeigth = view.getMeasuredHeight();
		Log.i(TAG, "oriWidth = " + oriWidth);
		Log.i(TAG, "oriHeigth = " + oriHeigth);

		ValueAnimator animator = ValueAnimator.ofFloat(1, scaleRatio);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();// 1 -> scaleRatio
				Log.d(TAG, "value = " + value);

				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.width = (int) (oriWidth * value);
				layoutParams.height = (int) (oriHeigth * value);
				if (scaleRatio == value) {
					view.setPadding(0, 18, 18, 0);
				}
				// 请求重新布局
				view.requestLayout();
			}
		});
		animator.setDuration(duration).start();
	}
}
