package com.wytiger.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 *  DB帮助类
 * @author wytiger
 * @date 2016年5月13日
 */
public class DbHelper extends SQLiteOpenHelper {

	private DbHelper(Context context) {
		super(context, DbConfig.NAME, null, DbConfig.VERSION);
	}

	private static DbHelper instance;

	public static DbHelper getInstance(Context context) {
		if (instance == null) {
			synchronized (DbHelper.class) {
				if (instance == null) {
					instance = new DbHelper(context);
				}
			}
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("DbHelper", "onCreate");
		// 创建通话记录表
		db.execSQL(DbConfig.OttCallLog.SQL_CREATE_TABLE);
	}

	/** 版本更新 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DbHelper", "onUpgrade");
	}

}
