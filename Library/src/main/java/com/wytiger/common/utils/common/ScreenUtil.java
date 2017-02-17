package com.wytiger.common.utils.common;

/**
 * Created by wytiger on 15/1/14.
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕相关
 */
/**
 * @author wytiger
 * @date 2016年1月21日
 */
public class ScreenUtil {
	/**
	 * 无标题栏, 若已设置全屏,则无需再调用这个方法
	 * 
	 * @param activity
	 *            ,需要在隐藏导航栏之前调用
	 */
	public static void noTitle(Activity activity) {
		// 去掉标题栏
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 隐藏导航栏
	 * 
	 * @param rootView
	 *            需要隐藏导航栏的根视图
	 */
	public static void hideNavigation(Activity activity) {
		final int HIDE_NAVIGATION_FLAG = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
		activity.getWindow().getDecorView().setSystemUiVisibility(HIDE_NAVIGATION_FLAG);
	}

	/**
	 * 默认竖屏 设置全屏，无状态栏,无标题栏,可能有导航栏
	 * 
	 * @param activity
	 */
	public static void fullScreen(Activity activity) {
		// 去掉标题栏
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉状态栏，全屏
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 强制竖屏
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	}

	/**
	 * 设置全屏，无状态栏,无标题栏,可能有导航栏
	 * 
	 * @param activity
	 * @param oritation
	 *            1:竖屏, 0:横屏
	 */
	public static void fullScreen(Activity activity, int oritation) {
		// 去掉标题栏
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉状态栏，全屏
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if (oritation == 0) {
			// 强制横屏
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			// 强制竖屏
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	// public static int getScreenWidth(Context context) {
	// WindowManager wm = (WindowManager)
	// (context.getSystemService(Context.WINDOW_SERVICE));
	// DisplayMetrics dm = new DisplayMetrics();
	// wm.getDefaultDisplay().getMetrics(dm);
	// int mScreenWidth = dm.widthPixels;
	// return mScreenWidth;
	// }
	//
	// public static int getScreenHeight(Context context) {
	// WindowManager wm = (WindowManager)
	// (context.getSystemService(Context.WINDOW_SERVICE));
	// DisplayMetrics dm = new DisplayMetrics();
	// wm.getDefaultDisplay().getMetrics(dm);
	// int mScreenHeigh = dm.heightPixels;
	// return mScreenHeigh;
	// }
	
	
	
	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度, 包括状态栏, 包括标题栏, 不包括导航栏(底部虚拟键)
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}



	// 若设置了全屏,则应该返回0??
	/**
	 * 获取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusHeight(Activity activity) {
		//通过id获取
		Resources resources = activity.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;

		// // 反射获取
		// Class<?> c = null;
		// Object obj = null;
		// Field field = null;
		// int x = 0, sbar = 0;
		// try {
		// c = Class.forName("com.android.internal.R$dimen");
		// obj = c.newInstance();
		// field = c.getField("status_bar_height");
		// x = Integer.parseInt(field.get(obj).toString());
		// sbar = activity.getResources().getDimensionPixelSize(x);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// return sbar;
		
//		Rect frame = new Rect();
//		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//		int statusBarHeight = frame.top;
//		return statusBarHeight;
		
	}

	// 若设置全屏或去除标题栏,则返回0
	/**
	 * 获取标题栏高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTitleHeight(Activity activity) {
		int titleBarHeight = 0;
		// content区域, 距离DecorView的高度
		int contentViewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		if (contentViewTop > 0) {// 若设置了全屏或无标题栏, 则contentViewTop为0
			// 标题栏高度
			titleBarHeight = contentViewTop - getStatusHeight(activity);
		} else {
			titleBarHeight = 0;
		}

		return titleBarHeight;
	}

	// TODO:若隐藏,则应该返回0??
	/**
	 * 获取导航栏(底部虚拟键)高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getNavigationHeight(Activity activity) {
		
		return getRealHeight(activity)-getScreenHeight(activity);
		
		//通过id获取
//		Resources resources = activity.getResources();
//		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//		int height = resources.getDimensionPixelSize(resourceId);
//		return height;

		// 反射获取
//		Class<?> c = null;
//		Object obj = null;
//		Field field = null;
//		int x = 0, sbar = 0;
//		try {
//			c = Class.forName("com.android.internal.R$dimen");
//			obj = c.newInstance();
//			field = c.getField("status_bar_height");
//			x = Integer.parseInt(field.get(obj).toString());
//			sbar = activity.getResources().getDimensionPixelSize(x);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		return sbar;
	}

	/**
	 * 获取屏幕总区域, 包括状态栏,标题栏,导航栏(底部虚拟键)
	 * 
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static DisplayMetrics getRealMetrics(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= 17) {
			// API 17之后使用，获取的像素宽高包含虚拟键所占空间
			activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
		} else {// 在API 17之前通过反射获取
			WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			Class c;
			try {
				c = Class.forName("android.view.Display");
				Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, displayMetrics);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return displayMetrics;
	}

	/**
	 * 获取真实尺寸.包括状态栏,标题栏,导航栏(底部虚拟键)
	 * 
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Point getRealSize(Activity activity) {
		Point outSize = new Point();
		if (Build.VERSION.SDK_INT >= 17) {
			// API 17之后使用，获取的像素宽高包含虚拟键所占空间
			activity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
		} else {// 在API 17之前通过反射获取
			WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			Class c;
			try {
				c = Class.forName("android.view.Display");
				Method method = c.getMethod("getRealSize", Point.class);
				method.invoke(display, outSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return outSize;
	}

	/**
	 * 获取屏幕总宽度, 包括状态栏,标题栏,导航栏
	 * 
	 * @param context
	 * @return
	 */
	public static int getRealWidth(Activity activity) {

		return getRealSize(activity).x;
	}

	/**
	 * 获取屏幕总高度, 包括状态栏,标题栏,导航栏
	 * 
	 * @param context
	 * @return
	 */
	public static int getRealHeight(Activity activity) {

		return getRealSize(activity).y;
	}

	/**
	 * 获取屏幕区域, 包括状态栏, 包括标题栏, 不包括导航栏
	 * 
	 * @param activity
	 * @return
	 */
	private static Point getScreenArea(Activity activity) {
		Display disp = activity.getWindowManager().getDefaultDisplay();
		Point outP = new Point();
		disp.getSize(outP);
		return outP;
	}

	public static int getScreenWidth(Activity activity) {

		return getScreenArea(activity).x;
	}

	/**
	 * 获取屏幕高度, 包括状态栏, 包括标题栏, 不包括导航栏
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {

		return getScreenArea(activity).y;
	}

	/**
	 * 获取app区域, 包括标题栏, 不包括状态栏, 不包括导航栏.
	 * 
	 * @param activity
	 * @return
	 */
	private static View getAppArea(Activity activity) {

		return activity.getWindow().getDecorView();
	}

	public static int getAppWidth(Activity activity) {

		return getAppArea(activity).getMeasuredWidth();
	}

	/**
	 * 获取app高度, 即DecorView高度,包括标题栏
	 * 
	 * @param activity
	 * @return
	 */
	public static int getAppHeight(Activity activity) {

		// return getAppArea(activity).getMeasuredHeight();

		return getScreenHeight() - getStatusHeight(activity);
	}

	/**
	 * 获取Content区域, 不包括标题栏, 不包括状态栏.
	 * 
	 * @param activity
	 * @return
	 */
	private static Rect getContentArea(Activity activity) {
		// 用户绘制区域
		Rect outRect = new Rect();
		activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);

		return outRect;
	}

	public static int getContentWidth(Activity activity) {

		return getContentArea(activity).width();
	}

	/**
	 * 获取content高度, 不包括标题栏
	 * 
	 * @param activity
	 * @return
	 */
	public static int getContentHeight(Activity activity) {

		// return getContentArea(activity).height();

		if (getTitleHeight(activity) > 0) {
			return getAppHeight(activity) - getTitleHeight(activity);
		} else {
			return getAppHeight(activity);
			// return getContentArea(activity).height();
		}
	}

}