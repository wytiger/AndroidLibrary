package com.wytiger.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 输入法工具类
 */
public class InputMethodUtil {
	private static final String TAG = "SoftInputUtil";

	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(Context mContext, EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(Context mContext, EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
	
	
	// ========================================================================================================
	
	
	/**
	 * 隐藏软键盘1
	 * 
	 * @param act
	 *            Activity
	 */
	public static void hideSoftInput(Activity act) {
		try {
			if (act == null) {
				return;
			}
			final View v = act.getWindow().peekDecorView();
			if (v != null && v.getWindowToken() != null) {
				// 输入法管理器
				InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
				// method 1
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				// method 2
				// imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(),
				// InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	/**
	 * 隐藏软键盘2
	 * 
	 * @param Activity
	 */
	public static void hideInputKeyboard(Activity act) {
		act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * 隐藏软键盘(实在关闭不了的时候使用)
	 * 
	 * @param act
	 *            Activity
	 * @param editText
	 *            编辑框
	 */
	public static void hideSoftInput(Activity act, EditText editText) {
		try {
			if (act == null || editText == null) {
				return;
			}
			if (editText.getWindowToken() != null) {
				InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	/**
	 * 显示软键盘1
	 * 
	 * @param act
	 *            Activity
	 */
	public static void showShoftInput(Activity act) {
		try {
			if (act == null) {
				return;
			}
			InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(null, 0);
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	/**
	 * 显示软键盘2
	 * 
	 * @param Activity
	 */
	public static void showInputKeyboard(Activity act) {
		act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	/**
	 * 切换软键盘（开- 关，关 - 开）
	 * 
	 * @param context
	 *            上下文对象
	 * 
	 */
	public static void showSoftInput(Context context) {
		try {
			InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	/**
	 * 软键盘是否显示(小白:测试好像没用，不建议使用。)
	 * 
	 * @param context
	 *            上下文对象
	 * @return boolean true表示软键盘在显示,否则返回false
	 */
	public static boolean isSoftInputShow(Context context) {
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			return imm.isActive();
		} catch (Exception e) {
			Log.w(TAG, e);
		}
		return false;
	}

}