package com.wytiger.common.utils.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

@SuppressLint("NewApi")
public class StorageUtil {  
	  
    // 判断SD卡是否被挂载  
    public static boolean isSDCardMounted() {  
      
        return Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED);  
    }  
  
    // 获取SD卡的根目录  
    public static String getSDCardBaseDir() {  
        if (isSDCardMounted()) {  
            return Environment.getExternalStorageDirectory().getAbsolutePath();  
        }  
        return null;  
    }  
  
    // 获取SD卡的完整空间大小，返回MB  
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static long getSDCardSize() {  
        if (isSDCardMounted()) {  
            StatFs fs = new StatFs(getSDCardBaseDir());  
            long count = fs.getBlockCountLong();  
            long size = fs.getBlockSizeLong();  
            return count * size / 1024 / 1024;  
        }  
        return 0;  
    }  
  
    // 获取SD卡的剩余空间大小  
    public static long getSDCardFreeSize() {  
        if (isSDCardMounted()) {  
            StatFs fs = new StatFs(getSDCardBaseDir());  
            long count = fs.getFreeBlocksLong();  
            long size = fs.getBlockSizeLong();  
            return count * size / 1024 / 1024;  
        }  
        return 0;  
    }  
  
    // 获取SD卡的可用空间大小  
    public static long getSDCardAvailableSize() {  
        if (isSDCardMounted()) {  
            StatFs fs = new StatFs(getSDCardBaseDir());  
            long count = fs.getAvailableBlocksLong();  
            long size = fs.getBlockSizeLong();  
            return count * size / 1024 / 1024;  
        }  
        return 0;  
    }  
  
    // 往SD卡的公有目录下保存文件  
    public static boolean saveFileToSDCardPublicDir(byte[] data, String type,  
            String fileName) {  
        BufferedOutputStream bos = null;  
        if (isSDCardMounted()) {  
            File file = Environment.getExternalStoragePublicDirectory(type);  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(new File(  
                        file, fileName)));  
                bos.write(data);  
                bos.flush();  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    bos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return false;  
    }  
  
    // 往SD卡的自定义目录下保存文件  
    public static boolean saveFileToSDCardCustomDir(byte[] data, String dir,  
            String fileName) {  
        BufferedOutputStream bos = null;  
        if (isSDCardMounted()) {  
            File file = new File(getSDCardBaseDir() + File.separator + dir);  
            if (!file.exists()) {  
                file.mkdirs();// 递归创建自定义目录  
            }  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(new File(  
                        file, fileName)));  
                bos.write(data);  
                bos.flush();  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    bos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return false;  
    }  
  
    // 往SD卡的私有Files目录下保存文件  
    public static boolean saveFileToSDCardPrivateFilesDir(byte[] data,  
            String type, String fileName, Context context) {  
        BufferedOutputStream bos = null;  
        if (isSDCardMounted()) {  
            File file = context.getExternalFilesDir(type);  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(new File(  
                        file, fileName)));  
                bos.write(data);  
                bos.flush();  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    bos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return false;  
    }  
  
    // 往SD卡的私有Cache目录下保存文件  
    public static boolean saveFileToSDCardPrivateCacheDir(byte[] data,  
            String fileName, Context context) {  
        BufferedOutputStream bos = null;  
        if (isSDCardMounted()) {  
            File file = context.getExternalCacheDir();  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(new File(  
                        file, fileName)));  
                bos.write(data);  
                bos.flush();  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    bos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return false;  
    }  
  
    // 保存bitmap图片到SDCard的私有Cache目录  
    public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap,  
            String fileName, Context context) {  
        if (isSDCardMounted()) {  
            BufferedOutputStream bos = null;  
            // 获取私有的Cache缓存目录  
            File file = context.getExternalCacheDir();  
  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(new File(  
                        file, fileName)));  
                if (fileName != null  
                        && (fileName.contains(".png") || fileName  
                                .contains(".PNG"))) {  
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);  
                } else {  
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
                }  
                bos.flush();  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (bos != null) {  
                    try {  
                        bos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            return true;  
        } else {  
            return false;  
        }  
    }  
  
    // 从SD卡获取文件  
    public static byte[] loadFileFromSDCard(String fileDir) {  
        BufferedInputStream bis = null;  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
  
        try {  
            bis = new BufferedInputStream(  
                    new FileInputStream(new File(fileDir)));  
            byte[] buffer = new byte[8 * 1024];  
            int c = 0;  
            while ((c = bis.read(buffer)) != -1) {  
                baos.write(buffer, 0, c);  
                baos.flush();  
            }  
            return baos.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                baos.close();  
                bis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
  
    // 从SDCard中寻找指定目录下的文件，返回Bitmap  
    public Bitmap loadBitmapFromSDCard(String filePath) {  
        byte[] data = loadFileFromSDCard(filePath);  
        if (data != null) {  
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);  
            if (bm != null) {  
                return bm;  
            }  
        }  
        return null;  
    }  
  
    // 获取SD卡公有目录的路径  
    public static String getSDCardPublicDir(String type) {  
        return Environment.getExternalStoragePublicDirectory(type).toString();  
    }  
  
    // 获取SD卡私有Cache目录的路径  
    public static String getSDCardPrivateCacheDir(Context context) {  
        return context.getExternalCacheDir().getAbsolutePath();  
    }  
  
    // 获取SD卡私有Files目录的路径  
    public static String getSDCardPrivateFilesDir(Context context, String type) {  
        return context.getExternalFilesDir(type).getAbsolutePath();  
    }  
  
    public static boolean isFileExist(String filePath) {  
        File file = new File(filePath);  
        return file.isFile();  
    }  
  
    // 从sdcard中删除文件  
    public static boolean removeFileFromSDCard(String filePath) {  
        File file = new File(filePath);  
        if (file.exists()) {  
            try {  
                file.delete();  
                return true;  
            } catch (Exception e) {  
                return false;  
            }  
        } else {  
            return false;  
        }  
    }  
    
    /**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}
}  
