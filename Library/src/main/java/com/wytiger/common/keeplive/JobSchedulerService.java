package com.wytiger.common.keeplive;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * 定时执行任务，服务需要在清单文件注册
 * 需要权限android.permission.RECEIVE_BOOT_COMPLETED
 * 同时需要为该service配置权限：android:permission="android.permission.BIND_JOB_SERVICE"
 * 
 * @author wytiger
 * @date 2016-8-18
 */
public class JobSchedulerService extends JobService {

	private static final String TAG = "JobSchedulerService";

	@Override
	public boolean onStartJob(JobParameters params) {
		Log.i(TAG, "JobSchedulerService onStartJob");
		
		
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Log.i(TAG, "JobSchedulerService onStartJob");		
		
		
		return true;
	}

	

}
