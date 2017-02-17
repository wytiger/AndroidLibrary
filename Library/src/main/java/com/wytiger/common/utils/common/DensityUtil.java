package com.wytiger.common.utils.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * px与dp转换工具类
 * @author wytiger
 *
 */
/**
 * 
 * @author wytiger
 * @date 2016年2月14日
 */
public class DensityUtil {

	/**
	 * dp转px
	 */
	public static int dp2px(Context ctx, float dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density + 0.5f);// 四舍五入处理

		return px;
		
//		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 */
	public static float px2dp(Context ctx, int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp = px / density;

		return dp;
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px
	 * 
	 * @param spValue
	 *            SP值
	 * @return 像素值
	 */
	public static int sp2px(float spValue) {
		float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		
		return (int) (spValue * fontScale + 0.5f);
		
//		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
	 * 
	 * @param pxValue
	 *            尺寸像素
	 * @return SP值
	 */
	public static int px2sp(float pxValue) {
		float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 获取密度density(1, 1.5, 2.0等)
	 * 
	 * @param ctx
	 * @return
	 */
	public static float getDensity() {

		return Resources.getSystem().getDisplayMetrics().density;
	}

	/**
	 * 获取屏幕密度dpi（每英寸的点数: dots-per-inch）
	 * 
	 * @param ctx
	 * @return
	 */
	public static float getDPI() {

		return Resources.getSystem().getDisplayMetrics().densityDpi;
	}

}
