/**
 * 
 */
package com.wytiger.common.utils.storage;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * 文件以及流处理工具类<br/>
 * 创建时间:2014-09-28
 * 
 * @author xuxiaoming
 * @version 1.0.1
 */
public final class FileUtil {
	/**
	 * 检测SD卡是否存在
	 */
	public static boolean checkSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
	
	/**
	 * 增加Android 内部媒体索引
	 * @param filePath
	 * @return
	 */
	public static boolean saveImgToGallery(Context ctx,String filePath) {
		
		if (!checkSDcard()) {
			return false;
		}
		try {
			ContentValues values = new ContentValues();
			values.put("datetaken", new java.util.Date().toString()); 
			values.put("mime_type", "image/jpg"); 
			values.put("_data", filePath);
			ContentResolver cr = ctx.getContentResolver();
			cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * 将文件保存到本地
	 */
	public static void saveFileCache(byte[] fileData, String folderPath,
			String fileName) {
		File folder = new File(folderPath);
		folder.mkdirs();
		File file = new File(folderPath, fileName);
		ByteArrayInputStream is = new ByteArrayInputStream(fileData);
		OutputStream os = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					os.write(buffer, 0, len);
				}
				os.flush();
			} catch (Exception e) {
				try {
					throw new Exception(FileUtil.class.getClass().getName(), e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				closeIO(is, os);
			}
		}
	}

	/**
	 * 从指定文件夹获取文件
	 * 
	 * @return 如果文件不存在则创建,如果如果无法创建文件或文件名为空则返回null
	 */
	public static File getSaveFile(String folderPath, String fileName) {
		File file = new File(getSavePath(folderPath) + File.separator
				+ fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取SD卡下指定文件夹的绝对路径
	 * 
	 * @return 返回SD卡下的指定文件夹的绝对路径
	 */
	public static String getSavePath(String folderName) {
		return getSaveFolder(folderName).getAbsolutePath();
	}

	/**
	 * 获取文件夹对象
	 * 
	 * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
	 */
	public static File getSaveFolder(String folderName) {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath()
				+ File.separator
				+ folderName
				+ File.separator);
		file.mkdirs();
		return file;
	}

	/**
	 * 输入流转byte[]<br>
	 * 
	 * <b>注意</b> 你必须手动关闭参数inStream
	 */
	public static final byte[] input2byte(InputStream inStream) {
		if (inStream == null) {
			return null;
		}
		byte[] in2b = null;
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		try {
			while ((rc = inStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			in2b = swapStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(swapStream);
		}
		return in2b;
	}

	/**
	 * 把uri转为File对象
	 */
	public static File uri2File(Activity aty, Uri uri) {
		if (getVersionCode(aty) < 11) {
			// 在API11以下可以使用：managedQuery
			String[] proj = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor actualimagecursor = aty.managedQuery(uri, proj, null, null,
					null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			return new File(img_path);
		} else {
			// 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
			String[] projection = { MediaStore.Images.Media.DATA };
			CursorLoader loader = new CursorLoader(aty, uri, projection, null,
					null, null);
			Cursor cursor = loader.loadInBackground();
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return new File(cursor.getString(column_index));
		}
	}

	/**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }
	
	/**
	 * 复制文件
	 * 
	 * @param from
	 * @param to
	 */
	public static void copyFile(File from, File to) {
		if (null == from || !from.exists()) {
			return;
		}
		if (null == to) {
			return;
		}
		FileInputStream is = null;
		FileOutputStream os = null;
		try {
			is = new FileInputStream(from);
			if (!to.exists()) {
				to.createNewFile();
			}
			os = new FileOutputStream(to);
			copyFileFast(is, os);
		} catch (Exception e) {
			try {
				throw new Exception(FileUtil.class.getClass().getName(), e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			closeIO(is, os);
		}
	}

	/**
	 * Copy file to file
	 * 
	 * @param source
	 *            Source File
	 * @param target
	 *            Target File
	 * @return Return copy isOk
	 */
	public static boolean copyFile2(File source, File target) {
		boolean bFlag = false;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			if (!target.exists()) {
				if (!target.createNewFile()) {
					// Create Error
					return false;
				}
			}
			in = new FileInputStream(source);
			out = new FileOutputStream(target);
			byte[] buffer = new byte[8 * 1024];
			int count;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			bFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			bFlag = false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bFlag;
	}

	/**
	 * 快速复制文件（采用nio操作）
	 * 
	 * @param is
	 *            数据来源
	 * @param os
	 *            数据目标
	 * @throws IOException
	 */
	public static void copyFileFast(FileInputStream is, FileOutputStream os)
			throws IOException {
		FileChannel in = is.getChannel();
		FileChannel out = os.getChannel();
		in.transferTo(0, in.size(), out);
	}

	/**
	 * 关闭流
	 * 
	 * @param closeables
	 */
	public static void closeIO(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {
				try {
					throw new Exception(FileUtil.class.getClass().getName(), e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 图片写入文件
	 * 
	 * @param bitmap
	 *            图片
	 * @param filePath
	 *            文件路径
	 * @return 是否写入成功
	 */
	public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
		boolean isSuccess = false;
		if (bitmap == null) {
			return isSuccess;
		}
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath),
					8 * 1024);
			isSuccess = bitmap.compress(CompressFormat.PNG, 70, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeIO(out);
		}
		return isSuccess;
	}

	/**
	 * 从文件中读取文本
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
			try {
				throw new Exception(FileUtil.class.getName() + "readFile---->"
						+ filePath + " not found");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return inputStream2String(is);
	}

	/**
	 * 从assets中读取文本
	 * 
	 * @param name
	 * @return
	 */
	public static String readFileFromAssets(Context context, String name) {
		InputStream is = null;
		try {
			is = context.getResources().getAssets().open(name);
		} catch (Exception e) {
			try {
				throw new Exception(FileUtil.class.getName()
						+ ".readFileFromAssets---->" + name + " not found");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return inputStream2String(is);
	}

	// //从assets 文件夹中获取文件并读取数据
	// public static String getFromAssets(String fileName)
	// {
	// String result = "";
	// try
	// {
	// InputStream in =
	// AppContext.getInstance().getResources().getAssets().open(fileName);
	// //获取文件的字节数
	// int lenght = in.available();
	// //创建byte数组
	// byte[] buffer = new byte[lenght];
	// //将文件中的数据读到byte数组中
	// in.read(buffer);
	// result = EncodingUtils.getString(buffer, ENCODING);
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 输入流转字符串
	 * 
	 * @param is
	 * @return 一个流中的字符串
	 */
	public static String inputStream2String(InputStream is) {
		if (null == is) {
			return null;
		}
		StringBuilder resultSb = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			resultSb = new StringBuilder();
			String len;
			while (null != (len = br.readLine())) {
				resultSb.append(len);
			}
		} catch (Exception ex) {
		} finally {
			closeIO(is);
		}
		return null == resultSb ? null : resultSb.toString();
	}


}