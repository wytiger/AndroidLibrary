package com.wytiger.lib.utils.storage;

import com.wytiger.lib.utils.common.SP;

import android.content.Context;

/**
 * 缓存工具类
 * 
 * @author wytiger
 * 
 */
public class CacheUtil {

	/**
	 * 设置缓存, key是url, value是str
	 */
	public static void setCache(String key, String value, Context ctx) {
		//可以将缓存放在文件中, 文件名url, 文件内容是str
		SP.setPrefString(ctx, key, value);
	}

	/**
	 * 获取缓存, key是url
	 */
	public static String getCache(String key, Context ctx) {
		return SP.getPrefString(ctx, key, null);
	}
}
