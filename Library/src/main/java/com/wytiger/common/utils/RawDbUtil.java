package com.wytiger.common.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * desc:
 * Created by wytiger at 2017/3/26 0026.
 */

public class RawDbUtil {
    public static final String dbDirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases";//sdpath用于存放保存的路径。
    public static final String dbFileName = "app_store.db";//filename用于保存文件名。

    @NonNull
    public static File copy(Context context, int rawId, String dbDirPath, String dbFileName) {
        File dir = new File(dbDirPath);
        //如果该目录不存在，创建该目录
        if (!dir.exists()) {
         boolean mkdirSuccess = dir.mkdirs();
            Log.i("RawDbUtil", "mkdirSuccess = " + mkdirSuccess);
        }
        String dbFilePath = dbDirPath + "/" + dbFileName;//其值等于database的路径
        File file = new File(dbFilePath);
        //如果文件不存在,写入文件
        if (!file.exists()) {
            try {
                InputStream inputStream = context.getResources().openRawResource(rawId);//将raw中的test.db放入输入流中
                FileOutputStream fileOutputStream = new FileOutputStream(dbFilePath);//将新的文件放入输出流中
                byte[] buff = new byte[8192];
                int len = 0;
                while ((len = inputStream.read(buff)) > 0) {
                    fileOutputStream.write(buff, 0, len);
                }
                fileOutputStream.close();
                inputStream.close();
            } catch (Exception e) {
                Log.i("RawDbUtil", "复制数据库失败");
                e.printStackTrace();
            }
        }
        Log.i("RawDbUtil", "filepath = " + file);
        return file;
    }

    public static SQLiteDatabase openDatabase(File dbFile) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);//利用openDatabase方法打开数据库。
        return database;
    }

}
