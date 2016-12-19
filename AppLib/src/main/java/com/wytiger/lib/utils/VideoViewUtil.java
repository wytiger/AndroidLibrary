package com.wytiger.lib.utils;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * 视频播放工具类
 * 
 * @author HUA-ZHONG-WEI
 *
 */
public class VideoViewUtil {
	private static String TAG = VideoViewUtil.class.getSimpleName();

	/** 是否显示视频进度条 */
	public static boolean isShowController = false;
	/** 视频是否全屏播放 */
	public static boolean isFullScreen = true;

	/**默认视频宽高*/
	public static int width = 540;
	public static int height = 960;

	/**
	 * 根据视频资源id播放视频
	 * 
	 * @param context  上下文
	 * @param videoView  视频组件
	 * @param resId  视频资源id
	 */
	public static void playById(Context context, VideoView videoView,
			int resId) {
		VideoView video = getVideoView(videoView);
		//设置监听
		setListener(video);
		Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
		video.setVideoURI(uri);
		
		MediaController mc = null;
		if (isShowController) {
			mc = new MediaController(context);
			video.setMediaController(mc);
			mc.setAnchorView(video);
		} else {
			video.setMediaController(null);
		}
		video.start();
		// video.requestFocus(); //获取焦点
	}

	/**
	 * 根据视频路径播放视频
	 * 
	 * @param context  上下文
	 * @param video  视频组件
	 * @param path  视频路径
	 */
	public static void playByPath(Context context, VideoView videoView, String path) {
		if (!isRightFile(context, path)){
			return;			
		}
		VideoView video = getVideoView(videoView);
		// setListener(video);
		video.setVideoPath(path);

		MediaController mc = null;
		if (isShowController) {
			mc = new MediaController(context);
			video.setMediaController(mc);
			mc.setAnchorView(video);
		} else {
			video.setMediaController(null);
		}
		video.start();

//		 video.requestFocus();
	}

	/**
	 * 控制是否全屏播放，定义相应的布局
	 * 
	 * @param video
	 * @return
	 */
	private static VideoView getVideoView(VideoView video) {
		RelativeLayout.LayoutParams params = null;
		if (isFullScreen) {
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			video.setLayoutParams(params);
		} else {
			params = new RelativeLayout.LayoutParams(width, height);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			video.setLayoutParams(params);
		}

		return video;
	}
	
	
	/**
	 * 设置视频控件的宽高,必须在调用playByXX()之前调用才有效;默认全屏
	 * @param width
	 * @param height
	 */
	public static void setWidthHeight(int width, int height) {
		VideoViewUtil.width = width;
		VideoViewUtil.height = height;
	}


	/**
	 * 释放资源, 停止播放
	 */
	public static void release(VideoView video) {
		if (video != null) {
			video.stopPlayback();
		}
	}	
	
	
	/**
	 * 判断文件是否存在，并且是否可读。
	 * @param path  文件路径	           
	 * @return
	 */
	public static boolean isRightFile(Context context, String path) {
		File file = new File(path);
		if (file.exists() && file.canRead()) {
			return true;
		}
		Toast.makeText(context, "文件不存在或无法读取", Toast.LENGTH_SHORT).show();
		return false;
	}

	
	/**
	 * 为视频播放设置监听
	 * @param video
	 */
	private static void setListener(VideoView video) {
		//播放准备
		video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				Log.i(TAG, "准备好播放视频啦");
				}
			});				
				
		//播放出错
		video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Log.i(TAG, "播放出错啦");
				return false;
			}
		});		
	}	

	
}
