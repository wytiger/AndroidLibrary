package com.wytiger.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wytiger.common.manager.AppManager;
import com.wytiger.lib.R;


/**
 * BaseActivity
 * @author wytiger
 * @date 2016年2月26日
 */
public abstract class BaseActivity extends Activity {
	private static final String TAG = BaseActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置内容视图
		setContentView(setActivityContentView());
		AppManager.addActivity(this);

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
	 * 初始化数据,子类根据需要实现。
	 */
	public void initData() {

	}

	/**
	 * 初始化视图,子类根据需要实现。
	 */
	public  void initView(){
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.removeActivity(this);
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
	 * 启动Activity
	 * 
	 * @param activity
	 */
	public void startActivity(Class<? extends Activity> activity) {
		Intent it = new Intent(this, activity);
		startActivity(it);		
	}
	
	
	/**
	 * 启动Activity,并指定了默认动画(啥也不做,覆盖系统动画, 可能引发部分手机后续Activity卡顿)
	 * 
	 * @param activity
	 *            要跳转到的Activity
	 */
	public void startActivity2(Class<? extends Activity> activity) {
		Intent it = new Intent(this, activity);
		startActivity(it);
		//activity切换动画
		overridePendingTransition(R.anim.anim_activity_enter,
				R.anim.anim_activity_exit);
	}

	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(enterAnim, exitAnim);
	}
}
