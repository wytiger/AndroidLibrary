package com.wytiger.common.helper;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public class WindowHelper {

	private static class SingletonStaticInternalClass {
		private static final WindowHelper instance = new WindowHelper();
	}

	public static WindowHelper getInstance() {
		return SingletonStaticInternalClass.instance;
	}

	/**
	 * 创建一个window并显示
	 * 
	 * @param context
	 * @param view
	 * @param width
	 * @param height
	 * @param gravity
	 * @param type
	 * @param flags
	 */
	public WindowManager newWindowAndShow(Context context, View view, int width, int height, int gravity, int type, int flags) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
		layoutParams.gravity = gravity;
		layoutParams.width = width;
		layoutParams.height = height;
		layoutParams.type = type;
		layoutParams.flags = flags;

		windowManager.addView(view, layoutParams);

		return windowManager;
	}

	/**
	 * 创建一个window并显示
	 * 
	 * @param context
	 * @param view
	 * @param layoutParams
	 */
	public WindowManager newWindowAndShow(Context context, View view, WindowManager.LayoutParams layoutParams) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(view, layoutParams);

		return windowManager;
	}

	/**
	 * 移除窗口
	 * 
	 * @param windowManager
	 * @param view
	 */
	public void removeWindow(WindowManager windowManager, View view) {
		windowManager.removeView(view);
	}
	
	
	
}
