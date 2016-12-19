package com.wytiger.lib.utils.encryp;

import java.security.MessageDigest;

/**
 * 单向加密MD5，不能解密，一般用于验证参数是否正确
 */
public class MD5Util {
	// 加密算法MD5
	private static final String ALGORITHM = "MD5";

	/**
	 * MD5加密，单向加密，不能解密的
	 */
	public static byte[] encryptByMD5(String data) {
		// String result = null;
		byte[] resultBytes = null;

		try {
			// 获取一个信息加密的加密对象，传递一个加密算法名称的参数
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);

			byte[] bytes = data.getBytes("utf-8");

			// 编码
			resultBytes = md.digest(bytes);

			// result = new String(resultBytes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultBytes;
	}

	/**
	 * 一般情况下，先使用MD5加密，再使用BASE64编码传输
	 */
	public static String encryptByMD5AndBASE64(String data) {

		byte[] bytes = encryptByMD5(data);

		String buff = null;
		try {
			buff = new String(bytes, "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}

		String result = Base64Util.encryptByBase64(buff);

		return result;
	}

}
