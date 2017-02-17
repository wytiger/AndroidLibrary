
package com.wytiger.lib.utils;

import android.annotation.TargetApi;
import android.os.StrictMode;

public class StrictModeUtil {

    /**
     * 启动严格模式
     */
    @TargetApi(14)
    private void startStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll() //ThreadPolicy
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll() //VmPolicy
                .penaltyLog()
                .build());
    }
}