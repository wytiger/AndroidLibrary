package com.wytiger.common.view.comm;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchPager extends ViewPager {
	private static final int Msg_What = 100;
	private static final long Delay_Millis = 3000;
	private Handler handler;

	public TouchPager(Context context) {
		super(context);
	}

	public TouchPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setHanlder(Handler handler) {

		this.handler = handler;
	}
	
	/**
	 * 触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		//手指按下,停止自动轮播
		case MotionEvent.ACTION_DOWN:
			if (handler != null) {
				handler.removeMessages(Msg_What);				
			}
			break;
			
		//手指移动
		case MotionEvent.ACTION_MOVE:
			//do nothing
			break;
			
		//手指抬起,开启自动轮播
		case MotionEvent.ACTION_UP:
			if (handler != null) {
				handler.sendEmptyMessageDelayed(Msg_What, Delay_Millis);		
			}
			break;

		default:
			break;
		}		
		return super.onTouchEvent(event);
	}

}
