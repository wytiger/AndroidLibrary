package com.wytiger.common.utils.common;

import android.text.TextUtils;
import android.util.Log;

import com.wytiger.common.BuildConfig;
import com.wytiger.common.gloable.AppApplication;
import com.wytiger.common.utils.storage.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by wytiger on 2017/2/24.
 * 日志工具类。
 * 控制是否打印log信息，并添加一些额外信息以方便定位
 */
public class LogUtil {
    private static boolean NEED_WRITE_LOG_FLAG = true;

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = AppApplication.getInstance().getPackageName();

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.v(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.e(TAG, msg);
        }
//        if (BuildConfig.DEBUG && NEED_WRITE_LOG_FLAG) {
//            writeLog2File(TAG, msg);
//        }
    }


    // 下面是传入自定义tag的函数
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.e(tag, msg);
        }
//        if (BuildConfig.DEBUG && NEED_WRITE_LOG_FLAG) {
//            writeLog2File(tag, msg);
//        }
    }


    private static String createLog(String log) {
        //通过线程栈帧元素获取相应信息
        StackTraceElement LogElement = Thread.currentThread().getStackTrace()[4];

        String fullClassName = LogElement.getClassName();
        String threadName = Thread.currentThread().getName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = LogElement.getMethodName();
        String fileName = LogElement.getFileName();
        int lineNumber = LogElement.getLineNumber();

        StringBuffer buffer = new StringBuffer();
        buffer.append("at ");//添加源码超链接
        buffer.append("[");
        buffer.append(threadName);
        buffer.append(":");
        buffer.append(className);
        buffer.append(".");
        buffer.append(methodName);
        buffer.append("(");
        buffer.append(fileName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append(")");
        buffer.append("] ");
        buffer.append(log);

        return buffer.toString();
    }


    /**
     * 给log日志添加非常多的有用信息： 当前线程id  线程名字  活跃线程总数  当前进程id  进程名字等
     */
//    private static String addExtraInfo(String logMe) {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(logMe + " ThreadId = " + Thread.currentThread().getId()
//                + " ThreadName = " + Thread.currentThread().getName()
//                + " activeThreadCount = " + Thread.activeCount()
//                + " ProcessId = " + android.os.Process.myPid()
//                + "processName = " + AppUtil.getCurProcessName(AppApplication.getInstance()));
//
//        return builder.toString();
//    }

    /**
     * 写日志信息到文件，tps/logs文件夹目录下，调试用，日志信息会自动换行
     *
     * @param tagDir
     * @param data
     */
    public synchronized static void writeLog2File(String tagDir, String data) {
        if (TextUtils.isEmpty(tagDir)) {
            tagDir = "app";
        }
        try {
            String logPath = StorageUtil.getSDCardBaseDir()  + "/tps/logs";
            File logDir = new File(logPath + "/" + tagDir);
            if (!logDir.exists()) {
                if (!logDir.mkdirs()) {
                    return;
                }
            }
            String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(logDir, fileName + ".txt");
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
            SimpleDateFormat sDateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            String dateString = sDateFormatYMD.format(new Date(System.currentTimeMillis()));
            buffer.append(dateString).append("   ").append(data).append("\r\n");

            RandomAccessFile raf = new RandomAccessFile(file, "rw");// "rw" 读写权限
            raf.seek(file.length());
            raf.write(buffer.toString().getBytes());
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

