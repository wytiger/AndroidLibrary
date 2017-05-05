package com.wytiger.common.utils;

/**
 * Description:
 * Created by wytiger on 2017/5/5
 */

public class PhoneUtil {

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取API等级（21、22、23 ...）
     *
     * @return
     */
    public static int getAPILevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android系统版本号（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}
