package com.wytiger.lib.helper;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @ClassName: ViewHelper
 * @Description: 作第三方嵌入时用此类获取各种资源文件，解决R.获取失败的问题
 * @author wytiger
 * @date 2015年6月3日 下午4:27:47
 * 
 */
public class ViewHelper {

	private static ViewHelper viewHelper;

	private ViewHelper() {
	}

	public static ViewHelper getInstance() {
		if (viewHelper == null) {
			viewHelper = new ViewHelper();
		}
		return viewHelper;
	}

	/** 获取本应用的包名 */
	private String getAppPackageName(Context context) {
		return context.getPackageName();
	}

	/** 根据name、type=layout找到资源id */
	public View getViewWithLayout(Context context, String name) {
		Resources resources = context.getResources();
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		int resid = resources.getIdentifier(name, "layout", getAppPackageName(context));
		View view = layoutInflater.inflate(resid, null);
		if (view != null) {
			view.setSoundEffectsEnabled(false);
		}
		return view;
	}

	/** 根据name、type=id找到资源id */
	public View getViewWithID(Context context, String name, View v) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "id", getAppPackageName(context));
		View view = v.findViewById(resid);
		if (view != null) {
			view.setSoundEffectsEnabled(false);
		}
		return view;
	}

	/** 根据name、type找到资源id */
	public int getResId(Context context, String name, String type) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, type, getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=drawable找到资源id */
	public int getResIdWithDrawable(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "drawable", getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=drawable找到资源id */
	public int getResIdWithLayout(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "layout", getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=style找到资源id */
	public int getResIdWithStyle(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "style", getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=anim找到资源id */
	public int getResIdWithAnim(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "anim", getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=color找到资源id */
	public int getResIdWithColor(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "color", getAppPackageName(context));
		return resid;
	}

	public int getResIdWithString(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "string", getAppPackageName(context));
		return resid;
	}

	/** 根据name、type=string找到资源id */
	public String getStringWithStringName(Context context, String name) {
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "string", getAppPackageName(context));
		String str = context.getString(resid);
		return str;
	}
}
