package com.wytiger.common.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author wytiger
 * @date 2016.01.22
 */
public class RegUtil {

    /**
     * 校验登录密码（6~16位，数字、字母、特殊符号组合两种或以上）
     * @param loginPassword
     * @return
     */
    public static boolean regLoginPassword(String loginPassword) {
        return loginPassword !=  null && Pattern.matches(AppRegex.LOGIN_PASSWORD, loginPassword);
    }

    /**
     * 校验手机号码
     * @param phoneNum
     * @return
     */
    public static boolean regChinaPhoneNum(String phoneNum){
        return phoneNum !=null&&Pattern.matches(AppRegex.MOBILE,phoneNum);
    }

    /**
     * 校验身份证号码
     * @param str
     * @return
     */
    public static boolean regIdentityNo(String str) {
       return  str!=null&&Pattern.matches(AppRegex.IDENTITY_NO,str);
    }

    /**
     * 校验货币
     * @param str
     * @return
     */
    public static boolean regMoney(String str) {
        return  str!=null&&Pattern.matches(AppRegex.MONEY,str);
    }

    /**
     * 校验银行卡
     * @param str
     * @return
     */
    public static boolean regCardNum(String str) {
        return  str!=null&&Pattern.matches(AppRegex.BANK_CARD_NO,str);
    }

    /**
     * 校验姓名
     * @param str
     * @return
     */
    public static boolean regRealName(String str) {
        return  str!=null&&Pattern.matches(AppRegex.REAL_NAME,str);
    }


}
