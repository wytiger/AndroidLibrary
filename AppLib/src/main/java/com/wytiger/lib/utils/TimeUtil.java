package com.wytiger.lib.utils;


/**
 * 整数(秒数)转换为时分秒格式(xx:xx:xx)
 * seconds  to xx:xx:xx  
 * @author yong.wu
 */
public class TimeUtil {

	/**
	 * seconds  to xx:xx:xx  
	 * @param time  总秒数
	 * @return
	 */
   public static String convert(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0){        	
        	return "00:00";          	
        }else {  
            minute = time / 60;  
            if (minute < 60) {//小于60分钟  
                second = time % 60;  
                timeStr = unitFormat(minute) + ":" + unitFormat(second);  
            } else { //大于60分钟 
                hour = minute / 60;  
                if (hour > 99){                	
                	return "99:59:59";  
                }
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
   /**
    * 小于或者大于等于10的处理
    * @param i
    * @return
    */
    private static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10){//小于10        	
        	retStr = "0" + Integer.toString(i);  
        }else{//大于等于10        	
        	retStr = "" + i;  
        }
        return retStr;  
    }  
	
	
}
