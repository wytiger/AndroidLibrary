package com.wytiger.common.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

public class PopWindowHelper {
	private Activity activity;
	private int popLayoutId;

	private PopupWindow popupWindow;
	private View popView;

	public PopWindowHelper(Activity activity, int popLayoutId) {
		this.activity = activity;
		this.popLayoutId = popLayoutId;
	}

	/**
	 * 初始化,设置宽高,是否可以获得焦点
	 * 
	 * @param width
	 * @param height
	 * @param focusable
	 */
	@SuppressWarnings("unused")
	private void init(int width, int height, boolean focusable) {

		popView = activity.getLayoutInflater().inflate(popLayoutId, null);
		
		popupWindow = new PopupWindow(popView, width, height, focusable);

		// 点击空白区域和默认返回按钮取消弹出Window
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 显示在anchor的左下角
	 * 
	 * @param anchor
	 */
	public void showAsDropDown(View anchor) {

		popupWindow.showAsDropDown(anchor);
	}

	/**
	 * 显示在anchor的左下角,并指定偏移
	 * 
	 * @param anchor
	 * @param xoff
	 * @param yoff
	 */
	public void showAsDropDown(View anchor, int xoff, int yoff) {

		popupWindow.showAsDropDown(anchor, xoff, yoff);

	}

	/**
	 * 显示在anchor的左下角, 并指定偏移与相对anchor的对齐方式
	 * 
	 * @param anchor
	 * @param xoff
	 * @param yoff
	 * @param gravity
	 */
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {

		popupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	/**
	 * 显示在哪个地方
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 */
	public void showAtLocation(View parent, int gravity, int x, int y) {

		popupWindow.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 获得指定id的view, 从而可以监听该view的点击等事件
	 * 
	 * @param viewId
	 * @return
	 */
	public View getView(int viewId) {

		return popView.findViewById(viewId);
	}

	/**
	 * 点击指定项跳转到指定的activity
	 * 
	 * @param viewId
	 * @param toActivity
	 */
	public void setOnClickItem2Activity(int itemId,
			final Class<? extends Activity> toActivity) {

		final View view = popView.findViewById(itemId);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == view) {
					Intent intent = new Intent(activity, toActivity);
					activity.startActivity(intent);
				}
			}
		});
	}

}
