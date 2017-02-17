package com.wytiger.common.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * description:补间动画工具类
 * Created by wytiger on 2016-10-19.
 */

public class AnimationUtil {
    private AnimationUtil() {
    }

    /**
     * 创建一个Alpha动画, 默认动画完成后保持状态
     *
     * @param fromAlpha
     * @param toAlpha
     * @param durationMillis
     * @return
     */
    public static Animation newAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        Animation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setFillAfter(true);
        animation.setDuration(durationMillis);

        return animation;
    }

    /**
     * 创建一个动画, 默认动画完成后保持状态
     *
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @param durationMillis
     * @return
     */
    public static Animation newScaleAnimation(float fromX, float toX, float fromY, float toY, long durationMillis) {
        Animation animation = new ScaleAnimation(fromX, toX, fromY, toY);
        animation.setFillAfter(true);
        animation.setDuration(durationMillis);

        return animation;
    }

    /**
     * 创建一个动画, 默认动画完成后保持状态
     *
     * @param fromXDelta
     * @param toXDelta
     * @param fromYDelta
     * @param toYDelta
     * @param durationMillis
     * @return
     */
    public static Animation newTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long durationMillis) {
        Animation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setFillAfter(true);
        animation.setDuration(durationMillis);

        return animation;
    }

    /**
     * 创建一个动画, 默认动画完成后保持状态
     *
     * @param fromDegrees
     * @param toDegrees
     * @param pivotX
     * @param pivotY
     * @param durationMillis
     * @return
     */
    public static Animation newRotateAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY, long durationMillis) {
        Animation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setFillAfter(true);
        animation.setDuration(durationMillis);

        return animation;
    }

}
