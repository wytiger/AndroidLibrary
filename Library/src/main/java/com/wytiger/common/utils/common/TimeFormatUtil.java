package com.wytiger.common.utils.common;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeFormatUtil {
	/**
	 * MM-dd月日格式
	 * 
	 * @param time
	 * @return
	 */
	public static String mdDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String fulldate = sdf.format(new Date(time));
		return fulldate;
	}

	/**
	 * MM-dd月日格式
	 * 
	 * @param time
	 * @return
	 */
	public static String mdDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String fulldate = sdf.format(time);
		return fulldate;
	}

	/**
	 * HH:mm:ss 时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String hmsDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String fulldate = sdf.format(new Date(time));
		return fulldate;
	}

	/**
	 * HH:mm:ss 时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String hmsDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String fulldate = sdf.format(time);
		return fulldate;
	}

	/**
	 * yyyy-MM-dd 年月日格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fulldate = sdf.format(new Date(time));
		return fulldate;
	}

	/**
	 * yyyy-MM-dd 年月日格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fulldate = sdf.format(time);
		return fulldate;
	}
	
	
	/**
	 * yyyy-MM-dd HH:mm:ss 年月日时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdhmsDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fulldate = sdf.format(new Date(time));
		return fulldate;
	}
	/**
	 * yyyy-MM-dd HH:mm:ss 年月日时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdhmDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String fulldate = sdf.format(new Date(time));
		return fulldate;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 年月日时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdhmsDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fulldate = sdf.format(time);
		return fulldate;
	}
	/**
	 * yyyy-MM-dd HH:mm 年月日时分格式
	 * 
	 * @param time
	 * @return
	 */
	public static String ymdhmDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String fulldate = sdf.format(time);
		return fulldate;
	}
}
