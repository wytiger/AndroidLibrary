package com.wytiger.mytest.heartbeat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class WriteService extends Service {
	private static final String TAG = "WriteService";
	public static long count = 0;
	private String path;
	private FileOutputStream fileOutputStream;

	public WriteService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");

		try {
			path = Environment.getExternalStorageDirectory() + "/heartbeat2.txt";
			fileOutputStream = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		count++;
		
		Log.i(TAG, "onStartCommand, count = " + count);

		new Thread(new Runnable() {

			@Override
			public void run() {
				String text = count + "\n";
				try {
					fileOutputStream.write(text.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

}
