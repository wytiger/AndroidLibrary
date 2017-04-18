package com.wytiger.common.manager;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AppActivityManager {
	private  static List<Activity> activitys = new LinkedList<Activity>();
	private  static List<Service> services = new LinkedList<Service>();
	
	private AppActivityManager() {
		//不能实例化
	}
	
	public static void addActivity(Activity activity) {
		activitys.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activitys.remove(activity);
	}

	public static void addService(Service service) {
		services.add(service);
	}

	public static void removeService(Service service) {
		services.remove(service);
	}


	private static void closeActivitys() {
		ListIterator<Activity> iterator = activitys.listIterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity != null) {
				activity.finish();
			}
		}
	}

	private static void closeServices() {
		ListIterator<Service> iterator = services.listIterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service != null) {
				service.stopSelf();
			}
		}
	}


	/**
	 * 关闭应用。关闭所有的Activity与Service
	 */
	public static void closeApplication() {
		closeActivitys();
		closeServices();
//		Process.killProcess(Process.myPid());
	}
	
	/**
	 * 根据包名启动应用
	 * @param ctx
	 * @param packageName
	 */
	public static void startApp(Context ctx, String packageName) {
		PackageManager pm = ctx.getPackageManager();
		Intent it = pm.getLaunchIntentForPackage(packageName);
		ctx.startActivity(it);
	}
	
	
	
}
