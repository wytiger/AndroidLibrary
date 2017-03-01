package com.wytiger.common.gloable;

import android.app.Application;

/**
 * Created by tiger
 * 2017/3/1 0001.
 */

public class WalletApplication extends Application {
    private static WalletApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static WalletApplication getInstance() {
        return sInstance;
    }

}
