package com.wytiger.lib.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * 对viewPager高度进行设置
 * @author Administrator
 */
public class ViewPagerUtil {

	/**
	 * 设置viewPage高度为屏幕的rate
	 */
	public static void setViewPagerHeigh(Activity activity, View view,
			double rate) {

		DisplayMetrics dm = new DisplayMetrics();

		// 必须执行这句
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		//获得屏幕宽高
		int screenHeight = dm.heightPixels;
		int screenWith = dm.widthPixels;

		//设置view的宽高
		LayoutParams params = (LayoutParams) view.getLayoutParams();
		params.width = screenWith;
		params.height = (int) (screenHeight * rate);
		view.setLayoutParams(params);
	}
}