
package com.wytiger.lib.utils.storage;

import java.io.File;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

@SuppressLint("SdCardPath")
/**
 * 清除本应用数据工具类
 * 主要功能: 清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 */
public class CleanDataUtil {

	/**
	 * 清除应用内部缓存(/data/data/com.xxx.xxx/cache) 
	 @param context
	  */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * 清除应用内部文件(/data/data/com.xxx.xxx/files) 	
	 * @param context
	 */
	public static void cleanInternalFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}
	
	
	/**
	 * 清除应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}
	
	
	/**
	 * 清除应用所有数据库(/data/data/com.xxx.xxx/databases)
	 * @param context
	 */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
	}
	
	
	/**
	 * 按名字清除本应用数据库
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}
		

	/**
	 * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 * @param context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}	
		
	
	/**
	 * 清除外部files下的内容(/mnt/sdcard/android/data/com.xxx.xxx/files)
	 * @param context
	 */
	public static void cleanExternalFiles(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalFilesDir(null));
		}
	}	

	/**
	 *清除本应用所有的数据
	 * @param context
	 * @param filepath
	 *  */
	public static void cleanAppData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanInternalFiles(context);
		cleanSharedPreference(context);
		cleanDatabases(context);
		cleanExternalCache(context);
		cleanExternalFiles(context);		
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}
	
	
	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}
	
	
	
	/**
	 * 删除文件夹
	 * @param folderPath: 文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */	
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	
	
	/**
	 * 根据文件路径删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	

}