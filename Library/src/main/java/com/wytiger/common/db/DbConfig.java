package com.wytiger.common.db;


/**
 *  DB配置类
 * @author wytiger
 * @date 2016-4-24
 */

public interface DbConfig {
	String NAME = "CallLog.db";
	int VERSION = 1;

	/**
	 * 合并后的通话记录
	 *
	 * @author wytiger
	 * @date 2016年5月13日
	 */
	public interface CallLog {
		String TABLE_NAME = "CallLog";
		
		String COLUMN_ID = "_id";
		String COLUMN_ADDRESS_NAME = "name";//通讯录姓名
		String COLUMN_FROM_NUMBER = "number";//来电号码
		String COLUMN_CALL_FLAG = "callflag";//Sys通话类型：1来电，2去电，3sys未接，4voicemails；OTT通话状态：0 ott未接，1 ott已接
		String COLUMN_TIME = "time";//来电时间
		String COLUMN_DURATION = "duration";//通话时长
		String COLUMN_MISSED_COUNT = "missed_count";//未接通话次数

		String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " ("
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_ADDRESS_NAME + " text,"
				+ COLUMN_FROM_NUMBER + " integer," 
				+ COLUMN_CALL_FLAG + " integer,"
				+ COLUMN_TIME + " integer,"
				+ COLUMN_DURATION + " integer,"
				+ COLUMN_MISSED_COUNT + " integer" + ")";
	}

	
	
	/**
	 * 微博OTT通话记录
	 *
	 * @author wytiger
	 * @date 2016年5月13日
	 */
	public interface OttCallLog {
		String TABLE_NAME = "OttCallLog";
		
		String COLUMN_ID = "_id";
		String COLUMN_ADDRESS_NAME = "name";//通讯录姓名
		String COLUMN_FROM_NUMBER = "number";//来电号码
		String COLUMN_CALL_FLAG = "callflag";//通话状态，0：未接，1：已接
		String COLUMN_TIME = "time";//来电时间
		String COLUMN_DURATION = "duration";//通话时长
		String COLUMN_MISSED_COUNT = "missed_count";//未接通话次数

		String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " ("
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_ADDRESS_NAME + " text,"
				+ COLUMN_FROM_NUMBER + " integer," 
				+ COLUMN_CALL_FLAG + " integer,"
				+ COLUMN_TIME + " integer,"
				+ COLUMN_DURATION + " integer,"
				+ COLUMN_MISSED_COUNT + " integer" + ")";
	}	
}
