
package com.wytiger.lib.utils.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Progress工具类
 *
 * @author wytiger
 * @date 2016-8-11
 */
public class ProgressUtil {

	/**
	 * 进度条
	 */
	public static ProgressDialog progressDialog;
	
	public static void  showProgress(Context context ,int resId) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context, ProgressDialog.THEME_TRADITIONAL);
			progressDialog.setMessage(context.getString(resId));
		}
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	public static void cancelProgress() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		}
	}
}
