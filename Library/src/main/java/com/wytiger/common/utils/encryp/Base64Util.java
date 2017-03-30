package com.wytiger.common.utils.encryp;

import android.util.Base64;

import java.io.IOException;

/**
 * BASE64编码解码
 * @author wytiger
 * @date 2016年5月18日
 */
public  class Base64Util {

	/**
	 * 编码
	 */
	public static String encryptByBase64(String data) {
		byte[] bytes = null;
		try {
			bytes = data.getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encodeToString(bytes, Base64.DEFAULT);

	}

	/**
	 * 编码
	 */
	public static String encryptByBase64(byte[] bytes) {
		return Base64.encodeToString(bytes, Base64.DEFAULT);

	}

	/**
	 * 译码
	 */
	public static String decryptByBase64ToStr(String encodeStr) {
		String result = null;
		try {
			byte[] decodeBytes = Base64.decode(encodeStr, Base64.DEFAULT);
			result = new String(decodeBytes, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 译码
	 */
	public static byte[] decryptByBase64ToBytes(String encodeStr) {
		return Base64.decode(encodeStr, Base64.DEFAULT);
	}

}
