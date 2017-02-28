package com.wytiger.common.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: wytiger
 * Time: 2016/7/4
 * Desc: Fragment基类
 * 注意：要求关联context必须从BaseActivity派生，否则会崩溃
 */
public abstract class BaseFragment extends Fragment {

	public BaseActivity baseActivity;
	
	/**
	 * fragment创建
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	/**
	 * 处理fragment的布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(getContentViewID(), null);
		initView();
		return rootView;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		baseActivity = (BaseActivity) context;
	}

	/**
	 * 初始化数据, 可以不实现
	 */
	public void initData() {

	}


	/**
	 * 初始化视图的方法
	 * @return
	 */
	public abstract void initView();



	protected abstract int getContentViewID();
	
	
    public void showLoadingDialog() {
        baseActivity.showLoadingDialog();
    }



    public void dismissLoadingDialog() {
        baseActivity.dismissLoadingDialog();
    }

}

