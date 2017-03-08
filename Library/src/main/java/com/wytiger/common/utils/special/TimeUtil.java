package com.wytiger.common.utils.special;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wytiger.common.utils.common.TimeFormatUtil;


import android.util.Log;

/**
 * 时间处理工具类
 * 
 * @author wytiger
 * @date 2016年5月10日
 */
public class TimeUtil {
	private static final String TAG = "CallLogUtil";

	/**
	 * 处理时间
	 * 
	 * @param time
	 * @return
	 */
	public static String handleDateTime(long time) {
		String timedate = TimeFormatUtil.ymdhmDate(time);

		// 判断
		if (isYesterday(timedate) == 0) {// 今天

			return timedate.split(" ")[1];
		} else if (isYesterday(timedate) == -1) {// 昨天

			return "昨天 ";
		} else {// 具体哪天

			return TimeFormatUtil.mdDate(time);
		}
	}
	
	
	/**
	 * 处理时间
	 * 
	 * @param time
	 * @return
	 */
	public static String handleDateTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date timedate = null;
		try {
			timedate = format.parse(time);
		} catch (ParseException e) {
			// e.printStackTrace();
			Log.e(TAG, e.toString());
		}

		// 判断
		if (isYesterday(time) == 0) {// 今天

			return time.split(" ")[1];
		} else if (isYesterday(time) == -1) {// 昨天

			return "昨天 ";
		} else {// 具体哪天

			return TimeFormatUtil.mdDate(timedate);
		}
	}

	
	/**
	 * 
	 * @param date
	 *            待比较的日期
	 * @return 0:今天，-1：昨天， -n：n天前
	 */
	private static int isYesterday(String date) {
		int day = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date d1 = new Date();// 当前时间
			Date d2 = sdf.parse(date);// 传进的时间

			// 时间差
			long cha = d2.getTime() - d1.getTime();
			day = new Long(cha / (1000 * 60 * 60 * 24)).intValue();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

}
