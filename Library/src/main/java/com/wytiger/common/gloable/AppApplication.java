package com.wytiger.common.gloable;

import android.app.Application;

/**
 * Created by tiger
 * 2017/3/1 0001.
 */

public class AppApplication extends Application {
    private static AppApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static AppApplication getInstance() {
        return sInstance;
    }

}
