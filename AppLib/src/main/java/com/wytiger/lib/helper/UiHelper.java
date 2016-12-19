package com.wytiger.lib.helper;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * UI帮助类
 * @author wytiger
 * @date 2016年2月17日
 */
/**
 *
 * @author wytiger
 * @date 2016年2月17日
 */
public class UiHelper {
	private static final String TAG = "UiHelper";

	/**
	 * 设置view的高度
	 *
	 * @param view
	 *            指定的view
	 * @param height
	 *            指定的高度，以像素为单位
	 */
	public static void setViewHeight(View view, int height) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = height;
		view.setLayoutParams(params);
	}

	/**
	 * 设置view的宽度
	 *
	 * @param view
	 *            指定的view
	 * @param width
	 *            指定的宽度，以像素为单位
	 */
	public static void setViewWidth(View view, int width) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.width = width;
		view.setLayoutParams(params);
	}

	

	/**
	 * 获取控件位置和偏移量,需要layout完成之后才能获取到,直接在onCreate()里无法获取位置;
	 * 可以在onWindowFocusChanged(boolean hasFocus)回调方法里调用来获取.
	 * 注意:控件不可见时会失败,无法获取(背景不要设为黑色)
	 * 
	 * @param view
	 * @param viewRect
	 * @param globalOffset
	 * @return
	 */
	public static boolean obtainViewLocation(View view, Rect viewRect,
			Point globalOffset) {
		boolean isSuccess = view.getGlobalVisibleRect(viewRect, globalOffset);

		return isSuccess;
	}

	/**
	 * 测量控件,之后就可以通过view.getMeasuredWidth(),view.getMeasuredHigh()获取到控件宽高了
	 * 
	 * @param view
	 * @return
	 */
	public static void measureView(View view) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
        int highSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
        
        //测量
        view.measure(widthSpec, highSpec); 
	}

	/**
	 * 重新Layout,以便一些原来未显示的控件显示出来.
	 * 尺寸大小或位置发生了变化请求layout,调用之后变会对View Tree进行重新measure与layout
	 * 
	 * @param view
	 */
	public static void reLayout(final View view) {

		boolean isMainThread = (Looper.myLooper() == Looper.getMainLooper());

		if (isMainThread) {// 在主线程中,直接请求重新layout
			view.requestLayout();
		} else {// 在子线程,则推送到主线程执行
			view.post(new Runnable() {
				@Override
				public void run() {
					view.requestLayout();
				}
			});

		}

	}

	/**
	 * 重绘View,更新view
	 * If the view is visible, onDraw(android.graphics.Canvas) will be called at some point in the future.
	 * 
	 * @param view
	 */
	public static void reDrawView(View view) {
		boolean isMainThread = (Looper.myLooper() == Looper.getMainLooper());

		if (isMainThread) {// 在主线程中
			view.invalidate();
		} else {
			view.postInvalidate();
		}

	}
	
	
	/**
	 * 设置listView高度，以适应内容
	 * @param listView  指定的listView
	 * @param hasFixedHeight  是否每个item的高度一致,提高计算效率
	 */
	public static void setListViewHeightMatchContent(ListView listView,
			boolean hasFixedHeight) {
		try {
			// 获取ListView对应的Adapter
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}

			int totalHeight = 0;
			int count = listAdapter.getCount();
			if (count > 0) {
				if (hasFixedHeight) {
					View listItem = listAdapter.getView(0, null, listView);
					listItem.measure(0, 0);
					totalHeight = count * listItem.getMeasuredHeight();
				} else {
					for (int i = 0; i < count; i++) {
						View listItem = listAdapter.getView(i, null, listView);
						// 修复缺陷：
						// item的父布局要为线性布局，或者参考网址http://www.chengxuyuans.com/Android/85072.html
						listItem.measure(0, 0);
						totalHeight += listItem.getMeasuredHeight();
					}
				}
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			// listView.getDividerHeight()获取子项间分隔符占用的高度
			// params.height最后得到整个ListView完整显示需要的高度
			listView.setLayoutParams(params);
			Log.d("aaa", "setListViewHeightMatchContent, h: " + totalHeight);
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

}
