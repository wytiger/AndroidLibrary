package com.wytiger.lib.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import java.util.Iterator;
import java.util.List;

/**
 * Created by breeze on 2015/9/15.
 */
public class ProcessUtil {

    private static volatile Boolean sMainProcess;
    private static final Object sMainLock = new Object();
    private static volatile String sProcessName;
    private static final Object sNameLock = new Object();

    public static String myProcessName(Context context) {
        if (sProcessName != null) {
            return sProcessName;
        } else {
            Object var1 = sNameLock;
            synchronized (sNameLock) {
                return sProcessName != null ? sProcessName : (sProcessName = obtainProcessName(context));
            }
        }
    }

    public static boolean isMainProcess(Context context) {
        if (sMainProcess != null) {
            return sMainProcess.booleanValue();
        } else {
            Object var1 = sMainLock;
            synchronized (sMainLock) {
                if (sMainProcess != null) {
                    return sMainProcess.booleanValue();
                } else {
                    String processName = myProcessName(context);
                    if (processName == null) {
                        return false;
                    } else {
                        sMainProcess = Boolean.valueOf(processName.equals(context.getApplicationInfo().processName));
                        return sMainProcess.booleanValue();
                    }
                }
            }
        }
    }

    private static String obtainProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> listTaskInfo = am.getRunningAppProcesses();
        if (listTaskInfo != null && listTaskInfo.size() > 0) {
            Iterator<RunningAppProcessInfo> i$ = listTaskInfo.iterator();

            while (i$.hasNext()) {
                ActivityManager.RunningAppProcessInfo proc = (ActivityManager.RunningAppProcessInfo) i$.next();
                if (proc != null && proc.pid == pid) {
                    return proc.processName;
                }
            }
        }

        return null;
    }
}
