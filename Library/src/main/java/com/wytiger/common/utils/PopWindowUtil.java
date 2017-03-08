package com.wytiger.common.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class PopWindowUtil {
	private static PopupWindow popupWindow;

	/**
	 * 初始化
	 * 
	 * @param activity
	 *            上下文
	 * @param layoutId
	 *            要显示的内容布局
	 * @param touchOutsideDismiss
	 *            点击外部区域是否要消失
	 */
	public static void initPopupWindow(Activity activity, int layoutId,
			final boolean touchOutsideDismiss) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(activity)
				.inflate(layoutId, null);

		popupWindow = new PopupWindow(contentView,
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT, true);

		// 可以touch
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				return touchOutsideDismiss;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		ColorDrawable dw = new ColorDrawable(0xb0000000); // 半透明
		// 设置SelectPicPopupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(dw);

	}

	/**
	 * 显示在指定控件的某个位置
	 * @param anchor
	 * @param xoff
	 * @param yoff
	 * @param gravity
	 */
	public static void showAsDropDown(View anchor, int xoff, int yoff,
			int gravity) {
		// 设置好参数之后再show
		popupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	/**
	 * 显示在哪个位置
	 * 
	 * @param parent 父控件
	 * @param gravity 
	 * @param x
	 * @param y
	 */
	public static void showAtLocation(View parent, int gravity, int x, int y) {
		// 设置好参数之后再show
		popupWindow.showAtLocation(parent, gravity, x, y);

	}

	/**
	 * 隐藏
	 */
	public static void dismiss() {

		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

}
