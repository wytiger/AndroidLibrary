package com.wytiger.mytest.utils;

import android.util.Log;

public class TestUtil {
	public static final String TAG = "TAG_COMM";
	/**
	 * 测试TryCatchFinally执行情况
	 */
	public static void testTryCatchFinally() {
		String str = null;
		try {
			Log.i(TAG, "try start");

			str = new String();
			str = (String) new Object();

			Log.i(TAG, "try end");
		} catch (Exception e) {
			Log.i(TAG, "catch start");

			e.printStackTrace();
			Log.e(TAG, "catch, e = " + e.getMessage());

			Log.i(TAG, "catch end");
		} finally {
			Log.i(TAG, "finally execute");
		}
	}

}
