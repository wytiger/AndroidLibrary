package com.wytiger.common.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wytiger.common.manager.AppActivityManager;


/**
 * BaseActivity
 * @author wytiger
 * @date 2016年2月26日
 */
public abstract class BaseActivity extends Activity {
	protected static final String TAG = BaseActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置内容视图
		setContentView(setActivityContentView());
		AppActivityManager.addActivity(this);

		initData();
		initView();

		 //打印当前activity是哪一个activity
		final String currentActivity = this.getClass().getSimpleName();
		Log.i(TAG, "currentActivity = " + currentActivity);		
	}

	/**
	 * 设置Activity的ContentView,抽象方法，子类必须实现
	 */
	public abstract int setActivityContentView();

	/**
	 * 初始化数据,在initView之前调用。
	 */
	public void initData() {

	}

	/**
	 * 初始化视图,在initData之后调用。
	 */
	public  void initView(){
		
	}

	@Override
	protected void onDestroy() {
		AppActivityManager.removeActivity(this);
		dismissLoadingDialog();
		super.onDestroy();
	}

	/**
	 * 显示可取消加载进度条
	 */
	public void showLoadingDialog() {



	}

	/**
	 * 将进度条关闭
	 */
	public void dismissLoadingDialog() {

	}


	/**
	 * 显示Toast
	 * 
	 * @param text
	 */
	protected void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 显示Toast
	 *
	 * @param text
	 */
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	
		/**
	 * replace  Fragment
	 * @param containerViewId
	 * @param fragment
     */
	protected void replaceFragment(int containerViewId, Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(containerViewId, fragment)
				.commit();
	}

	/**
	 * switch  Fragment
	 * @param containerViewId
	 * @param fromFragment
	 * @param toFragment
     */
	protected void switchFragment(int containerViewId, Fragment fromFragment, Fragment toFragment) {
		if (fromFragment != toFragment) {
			if (toFragment.isAdded()) {
				getFragmentManager().beginTransaction()
						.hide(fromFragment).show(toFragment).commit();
			} else {
				getFragmentManager().beginTransaction()
						.hide(fromFragment).add(containerViewId, toFragment).commit();
			}
		}
	}

	
	
	/**
	 * 启动Activity
	 * 
	 * @param activity
	 */
	public void startActivity(Class<? extends Activity> activity) {
		Intent it = new Intent(this, activity);
		startActivity(it);
	}

}
