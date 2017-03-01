package com.wytiger.common.utils.common;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Log统一管理类
 * 
 * @author wytiger
 * 
 */
public class LogUtil {
	private static final String TAG = "LogUtil";
	/** 是否需要打印日志 */
	public static boolean isDebug = true;
	/** 是否需要将日志写入文件 */
	private static boolean needWrite2File = true;
	private static final int LogFile = 0;
	/** 时间转换格式 */
    private static SimpleDateFormat sDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINESE);
    /** 文件默认路径 */
    public static final String FILE_PATH_DEFAULT = "wytiger";
    
    // ===================================================================================
    private LogUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

    // =============================下面四个是默认tag的函数====================================
	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void w(String msg) {
		if (isDebug)
			Log.w(TAG, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	 // ===============================下面是传入自定义tag的函数==================================
	public static void v(String tag, String msg) {
		if (isDebug)
			Log.v(tag, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (isDebug)
			Log.w(tag, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.e(tag, msg);
		if (needWrite2File) {
			writeLog2File(TAG, msg);
		}
	}
	
	
	
    
    /**
	 * 写日志信息到文件，调试用，日志信息会自动换行
     *
	 * @param fileName 文件名
	 * @param data 写入的数据
	 */
	public synchronized static void writeLog2File(String fileName, String data) {
		if (TextUtils.isEmpty(fileName)) {
			return;
		}
		String path = getSdCardPath()+"/"+LogFile;
		try {
			File filePath = new File(path);
			if (!filePath.exists()) {
				if (!filePath.mkdirs()) {
					return;
				}
			}
			File files = new File(path + "/" + fileName);
			if(!files.exists()) {
				if(!files.mkdirs()) {
					return;
				}
			}
			String childFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			File file = new File(filePath + "/" + fileName + "/" + childFileName + ".txt");
			if (file.exists()) {
				if (file.length() > 200 * 1024) {
					file.delete();
					if (!file.createNewFile()) {
						return;
					}
				}
			} else {
				if (!file.createNewFile()) {
					return;
				}
			}
			StringBuilder buffer = new StringBuilder();
			String dateString = sDateFormater.format(new Date(System.currentTimeMillis()));
			buffer.append(dateString).append("   ").append(data).append("\r\n");

			RandomAccessFile raf = new RandomAccessFile(file, "rw");// "rw" 读写权限
			raf.seek(file.length());
			raf.write(buffer.toString().getBytes());
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取SD卡的保存文件路径
	 * 部分手机自带SD卡，自带的SD卡文件夹命名为sdcard-ext或其它，用系统自带方法无法检测出
	 * 返回格式为 "/mnt/sdcard/AboutYX/" 或 "/mnt/sdcard-ext/AboutYX/"
	 * @return
	 */
	public static String getSdCardPath() {
		String path = "";
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + FILE_PATH_DEFAULT);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = file.getAbsolutePath();
		} else {
			File mntFile = new File("/mnt");
			File[] mntFileList = mntFile.listFiles();
			if (mntFileList != null) {
				for (int i = 0; i < mntFileList.length; i++) {
					String mmtFilePath = mntFileList[i].getAbsolutePath();
					String sdPath = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					if (!mmtFilePath.equals(sdPath)
							&& mmtFilePath.contains(sdPath)) {
						File file = new File(mmtFilePath + File.separator + FILE_PATH_DEFAULT);
						if (!file.exists()) {
							file.mkdirs();
						}
						path = file.getAbsolutePath();
					}
				}
			}
		}
		return (path == null || path.length() == 0) ? "" : path;
	}
	
}