/**
 * 
 */
package com.wytiger.lib.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author xuxiaoming
 * 
 */
public class StringUtil {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(170)|(14[5,7]))\\d{8}$");
    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        if (isEmpty(email))
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        if (isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }

    /**
     * 字符串转整数
     * 
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * String转double
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     * 
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        try {
            Integer.parseInt(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * byte[]数组转换为16进制的字符串。
     * 
     * @param data
     *            要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     * 
     * @param s
     *            16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }
    
    /**
     * 数字,保留两位小数
     * @param str
     * @return
     */
    public static String numHold2Decimal(double d){
    	String str = "";
    	try{
    		DecimalFormat df = new DecimalFormat("0.00");
    		str = df.format(d); 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return str;
    }

    
    /**
     * 百分数,保留两位小数
     * @param str
     * @return
     */
    public static String perHold2Decimal(double d){
    	String str = "";
    	try{
    		DecimalFormat df = new DecimalFormat("0.00%");
    		str = df.format(d); 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return str;
    }
    
    /**
     * 加密手机号
     * @param phoneNum
     * @return
     */
    public static String getCryptionPhone(String phoneNum) {
    	if (phoneNum.length() > 8) {
    		return phoneNum.substring(0, 3)+"****"+phoneNum.substring(7, phoneNum.length());
		} else if (phoneNum.length() > 3) {
			return phoneNum.substring(0, 3)+"****";
		} else {
			return phoneNum;
		}
	}
    
    
    //-----------------------------------------------------------------------
    

    private static final int VALUE_10 = 10;
    private static final int VALUE_13 = 13;
    private static final int VALUE_32 = 32;
    private static final int VALUE_60 = 60;
    private static final int VALUE_62 = 62;
    private static final int VALUE_38 = 38;
    private static final int VALUE_34 = 34;
    private static final int VALUE_165 = 165;
    private static final int VALUE_174 = 174;
    private static final int VALUE_8482 = 8482;
    private static final int VALUE_8364 = 8364;
    private static final int VALUE_169 = 169;
    private static final int OffsetBig = 256;
    private static final int OffsetSmall = 16;

    private static final int MOBIL_F_TAG = 3;
    private static final int MOBIL_L_TAG = 7;
    private static final int WEEK_TAG = 7;
    private static final int MOBIL_P_TAG_PREFIX = 6;
    private static final int MOBIL_N_TAG_PREFIX = 10;

    private static final String ENCRYPT_SALTE = "paidui888";
    private static final String TAG = "StringUtil";

    /**
     * 字符串去空格，回车，换行，制表符
     *
     * @param str 要修改的字符串
     * @return 修改完成的字符串
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 将输入的字符串进行html编码
     *
     * @param input 输入的字符串
     * @return html编码后的结果
     */
    public static String htmEncode(String input) {
        if (null == input || "".equals(input)) {
            return input;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int j = input.length();
        for (int i = 0; i < j; i++) {
            char c = input.charAt(i);
            switch (c) {
                case VALUE_60:
                    stringBuilder.append("&lt;");
                    break;
                case VALUE_62:
                    stringBuilder.append("&gt;");
                    break;
                case VALUE_38:
                    stringBuilder.append("&amp;");
                    break;
                case VALUE_34:
                    stringBuilder.append("&quot;");
                    break;
                case VALUE_169:
                    stringBuilder.append("&copy;");
                    break;
                case VALUE_174:
                    stringBuilder.append("&reg;");
                    break;
                case VALUE_165:
                    stringBuilder.append("&yen;");
                    break;
                case VALUE_8364:
                    stringBuilder.append("&euro;");
                    break;
                case VALUE_8482:
                    stringBuilder.append("&#153;");
                    break;
                case VALUE_13:
                    if (i < j - 1 && input.charAt(i + 1) == VALUE_10) {
                        stringBuilder.append("<br>");
                        i++;
                    }
                    break;
                case VALUE_32:
                    if (i < j - 1 && input.charAt(i + 1) == ' ') {
                        stringBuilder.append(" &nbsp;");
                        i++;
                        break;
                    }
                default:
                    stringBuilder.append(c);
                    break;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 判断字符串是否为null或者空字符串
     *
     * @param input 输入的字符串
     * @return 如果为null或者空字符串，返回true；否则返回false
     */
    public static boolean isNullOrEmpty(String input) {
        return null == input || "".equals(input) || input.trim().length() == 0;
    }

    /**
     * 判断字符串中是否含有中文字符
     *
     * @param s 需要判断的字符串
     * @return true为包含中文
     */
    public static boolean containChinese(String s) {
        if (null == s) {
            return false;
        }

        Pattern pattern = Pattern.compile(".*[\u4e00-\u9fbb]+.*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * 判断输入的是否为汉字字符
     *
     * @return 是否为中文
     */
    public static boolean isChinese(char a) {
        // return (v >=19968 && v <= 171941);
        return String.valueOf(a).matches("[\u4E00-\u9FA5]"); // 利用正则表达式，经测试可以区分开中文符号
    }

    /**
     * 判断输入的是否为汉字字符
     *
     * @return 是否为中文
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isChinese(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取MD5加密后Hash字符串
     *
     * @param strOriginal 初始字符串
     * @return MD5加密后Hash字符串
     */
    private static String getMd5Hash(String strOriginal) {
        StringBuilder sbList = new StringBuilder();
        try {
            MessageDigest mMD5 = MessageDigest.getInstance("MD5");
            byte[] data = strOriginal.getBytes("utf-8");
            byte[] dataPWD = mMD5.digest(data);
            for (int offset = 0; offset < dataPWD.length; offset++) {
                int i = dataPWD[offset];
                if (i < 0) {
                    i += OffsetBig;
                }
                if (i < OffsetSmall) {
                    sbList.append("0");
                }
                sbList.append(Integer.toHexString(i));
            }
            return sbList.toString();
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    /**
     * 处理手机号码
     *
     * @param userMobile 需要处理的手机号码
     * @return String 处理后的手机号码
     */
    public static String getProcessedMobile(String userMobile) {
        String mProcessedDrawMobile = "";
        if (!StringUtil.isNullOrEmpty(userMobile)) {
            // 判断是否是+86开头的手机号码
            if ('+' == userMobile.charAt(0) && userMobile.length() >= MOBIL_N_TAG_PREFIX) {
                mProcessedDrawMobile = userMobile.substring(0, MOBIL_P_TAG_PREFIX) + "****"
                        + userMobile.substring(MOBIL_N_TAG_PREFIX);
            } else if ('+' != userMobile.charAt(0) && userMobile.length() >= MOBIL_L_TAG) {
                mProcessedDrawMobile = userMobile.substring(0, MOBIL_F_TAG) + "****"
                        + userMobile.substring(MOBIL_L_TAG);
            } else {
                mProcessedDrawMobile = userMobile;
            }
        }
        return mProcessedDrawMobile;
    }

    /**
     * 获取当期是星期几（从星期天开始）
     *
     * @param weeknum 当前是第几天（0-6）
     * @return 星期*
     */
    public static String getDayOfWeek(int weeknum) {
        weeknum--;
        if (weeknum > WEEK_TAG) {
            weeknum = weeknum % WEEK_TAG;
        }

        if (weeknum < 0) {
            weeknum = -weeknum;
        }

        String[] weekArray = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekArray[weeknum];

    }

    /**
     * 重载方法，获取当期是星期几
     *
     * @param c 日历
     * @return 星期*
     */
    public static String getDayOfWeek(Calendar c) {
        return getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 检查是不是中文
     *
     * @param str 检查字符串
     * @return boolean 是否为中文
     */
    public static boolean checkStringIsChinese(String str) {
        if (null == str) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 转换保留小数位 字符串
     *
     * @param i      小数位数
     * @param numStr 数字字符串
     * @return String
     */
    public static String getDecimalFormat(int i, String numStr) {
        try {
            if (numStr != null && !"".equals(numStr)) {
                BigDecimal bd = new BigDecimal(numStr);
                bd = bd.setScale(i, BigDecimal.ROUND_HALF_UP);

                return bd.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 转换保留小数位
     *
     * @param i   小数位数
     * @param num 要转换的数字
     * @return double 返回类型
     */
    public static double decimalFormat(int i, Double num) {
        String temp = getDecimalFormat(i, num.toString());
        return Double.valueOf(temp);
    }

    /**
     * 检查是否是字符串
     *
     * @param input 被检查字符串
     * @return 是否是数字组成的字符串，包括0开头;<br>
     * 如果为空或者空字符串，返回true；比如："011"返回true，"a11"返回false
     */
    public static boolean isNumberString(String input) {
        if (!isNullOrEmpty(input)) {
            if (input.matches("[0-9]+")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param str 带检测的字符串
     * @return String 去掉多余的.与0
     */
    public static String filterZeroAndDot(String str) {
        if (!isNullOrEmpty(str) && str.indexOf(".") > 0) {
            // 去掉多余的0
            str = str.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            str = str.replaceAll("[.]$", "");
        }
        return str;
    }

    public static String getDecimalFormatString(double input, int digits) {
        BigDecimal bd = new BigDecimal(input);
        bd = bd.setScale(digits, BigDecimal.ROUND_HALF_UP);

        return filterZeroAndDot(bd.toString());
    }

    /**
     * 半角转换为全角（用于textview换行不整齐时使用）
     *
     * @param input 半角字符串
     * @return 全角字符串
     */
    public static String toDBC(String input) {
        if (input == null) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String sortString(String str) {
        String[] orderDishID = str.split(",");
        Arrays.sort(orderDishID);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orderDishID.length; i++) {
            sb.append(orderDishID[i]);
            if (i + 1 < orderDishID.length) {
                sb.append(",");
            }
        }
        orderDishID = null;
        str = null;
        return sb.toString();
    }

    /**
     * 从第一个分隔符到结尾，去掉分隔符前面的内容(去前缀)
     *
     * @param resStr     内容字符串
     * @param spreateStr 前缀标记
     * @return
     */
    public static String subStringFromSpreateToEnd(String resStr, String spreateStr) {
        if (!StringUtil.isNullOrEmpty(resStr) && !StringUtil.isNullOrEmpty(spreateStr) && resStr.contains(spreateStr)) {
            resStr = resStr.substring(resStr.indexOf(spreateStr) + 1);
        }
        return resStr;
    }

    /**
     * 为字符串加入分隔符，如果为空
     *
     * @param resStr
     * @param appendStr
     * @param spreateStr
     * @return 为字符串加入分隔符，如果为空
     */
    public static String appendStringWithSpreate(String resStr, String appendStr, String spreateStr) {
        if (!StringUtil.isNullOrEmpty(spreateStr)) {
            if (StringUtil.isNullOrEmpty(resStr)) {
                resStr = appendStr;
            } else {
                resStr += spreateStr + appendStr;
            }
        }
        return resStr;
    }

    public static boolean isMobileNumber(String mobiles) {
        Pattern p = Pattern.compile("^(13|14|15|17|18)\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }


    /**
     * 是否是汉字，英文，数字或者其中几个组成
     *
     * @param s 字符串
     * @return boolean 是否是汉字，英文，数字或者其中几个组成
     */
    public static boolean isValidEngOrChOrNum(String s) {
        if (null == s) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\u4E00-\u9FA5A-Za-z0-9]*$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * 把list集合数据转化成带逗号的字符串
     *
     * @param list list集合数据
     * @return String 带逗号的字符串
     */
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字符串转化为数字
     *
     * @param str 字符串
     */
    public static int toDigit(String str) {
        if (isNumberString(str)) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }

}
