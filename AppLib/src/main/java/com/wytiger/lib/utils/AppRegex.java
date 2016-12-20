package com.wytiger.lib.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式常量
 * @author yangzhou
 * @date 2016-2-26 下午5:29:19
 */
public class AppRegex {
    /** 手机号 **/
    public static final String MOBILE = "^(\\+86)?1[3-5,7,8]\\d{9}$";
    public static final String LOGIN_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![^a-zA-Z0-9]+$)\\S{8,20}$";
    /**必须包含数字*/
    public static final String MUST_HAVA_DIGIT="(?=.*[0-9])";
    /**必须包含小写或大写字母*/
    public static final String MUST_HAVA_ABC="(?=.*[a-zA-Z])";
    /* 必须包含特殊符号*/
    public static final String MUST_HAVA_SPECIAL_CHAR="(?=([\\x21-\\x7e]+)[^a-zA-Z0-9])";
    /* 至少8个字符，最多30个字符*/
    public static final String LIMIT_LENTH=" .{8,30}";
    /** 邮箱号 **/
    public static final String EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    /** 真实姓名 **/
    public static final String REAL_NAME = "^[\\u4e00-\\u9fa5]{2,6}|[\\u4e00-\\u9fa5]{1,6}·[\\u4e00-\\u9fa5]{1,6}$";
    /** 身份证号 **/
    public static final String IDENTITY_NO = "^\\d{17}[0-9Xx]$|^\\d{15}$";
    /** 金额为分的格式  */
    public static final String MONEY_FEN = "[0-9]+";

    /** 金钱的格式  */
    //public static final String MONEY = "^\\d+(\\.\\d\\d)?$";
    public static final String MONEY ="(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
    /** 数字 **/
    public static final String NUMBER = "^[0-9]|([1-9]\\d*)$";

    /** 卡号  **/
    public static final String BANK_CARD_NO = "^\\d{15,19}$";

    public static final String CREDIT_CVV = "^\\d{3}$";
    public static final String CREDIT_EXPIRE = "^\\d{2}0[1-9]$|^\\d{2}1[012]$";
    public static final String VERIFY_CODE = "^\\d{6}$";
    public static final String AMOUNT = "^[1-9]\\d{0,11}$";
    public static final String AMOUNT_ZERO = "^0|[1-9]\\d{0,11}$";
    public static final String PARTNER = "^\\d{10}$";
    public static final String TIME_14 = "^\\d{14}$";
    public static final String IDENTITY_TYPE = "^01$";

    public static final String PAY_TYPE = "^1|2$";
    public static final String BIND_NO = "^[\\d]{26}$";

    public static final String SECURITY_LEVER = "^0|1$";

    /**请求服务版本号**/
    public static final String VERSION ="^[1-9]$";
    /**app版本号**/
    public static final String APP_VERSION ="^[1-9]$";
    /**支付密码校验**/
    public static final String PAYPWD = "^\\d{6}$";


    public static final String TIME_13 = "^\\d{13}$";

    public static void main(String[] args) {
        if (Pattern.matches(AppRegex.MOBILE,"15800000005"))
        {
            System.out.println("----true---------");
        }
    }
}
