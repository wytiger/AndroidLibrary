package com.wytiger.common.view;

import java.io.FileDescriptor;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * 播放视频的SurfaceView
 * @author wytiger
 * @date 2016年2月27日
 */
public class VideoSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	private static final String TAG = "VideoSurfaceView";

	/**
	 * Allows you to control the surface size and format, edit the pixels in the
	 * surface, and monitor changes to the surface.
	 */
	private SurfaceHolder surfaceHolder;
	/**
	 * 视频播放器
	 */
	private MediaPlayer player;
	/**
	 * 视频源
	 */
	private String videoSource;
	//异常详情
	private String exceptionDetail = "The video source is not been nitialized, "
			+ "Please call the method setDataSource(String videoSource) to set the data source";

	
	/**
	 * 构造方法, 用于直接new对象
	 * 
	 * @param context
	 */
	public VideoSurfaceView(Context context) {
		this(context, null);
	}

	/**
	 * 构造方法, 用于在xml文件里设置
	 * 
	 * @param context
	 * @param attrs
	 */
	public VideoSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获得SurfaceHolder并添加回调
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
	}

	/**
	 * surface创建
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setDisplay(surfaceHolder);
		
		try {
			if (videoSource == null) {
				throw new Exception(exceptionDetail);
			}else {
				// 设置数据源
				player.setDataSource(videoSource);
				// 资源准备
//				player.prepare();//主线程同步准备,可能阻塞UI
				player.prepareAsync();//子线程异步准备
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * surface改变
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.i(TAG, "surfaceChanged format=" + format + ", width=" + width
				+ ", height=" + height);

	}

	/**
	 * surface销毁
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (player.isPlaying()) {
			player.stop();
		}
		// 释放资源
		player.release();

	}	
	
	
	/**
	 * 设置数据源
	 * 
	 * @param videoPath
	 */
	public void setDataSource(String videoPath) {
		this.videoSource = videoPath;
	}
	
	/**
	 * 设置数据源
	 * 
	 * @param videoUri
	 */
	public void setDataSource(Uri videoUri) {
		String videoPath = videoUri.getPath();
		this.videoSource = videoPath;
	}
	
	/**
	 * 设置数据源
	 * 
	 * @param videoUri
	 */
	public void setDataSource(FileDescriptor fd) {
		String videoPath = fd.toString();
		this.videoSource = videoPath;
	}	

	
	/**
	 * 开始播放
	 */
	public void start() {
		player.pause();
	}

	/**
	 * 暂停播放
	 */
	public void pause() {
		player.pause();
	}

	/**
	 * 停止播放
	 */
	public void stop() {
		player.stop();
	}

	/**
	 * 重置播放器到未初始化状态; 之后必须重新设置数据源并调用prepare()
	 */
	public void reset() {
		player.reset();
	}

	/**
	 * 释放资源
	 */
	public void release() {
		player.release();
	}

	/**
	 * 获取已经播放的百分比
	 * @return
	 */
	public float getPercentage() {
		return (float)player.getCurrentPosition()/player.getDuration();
	}
	
	
	/**
	 * 获取当前位置
	 */
	public int  getCurrentPosition() {
		return player.getCurrentPosition();
	}
	
	/**
	 * 获取视频时长
	 * @return
	 */
	public int getDuration() {
		return player.getDuration();
	}
	
	/**
	 * 获得宽
	 * @return
	 */
	public int getVideoWidth() {
		return player.getVideoWidth();
	}
	
	/**
	 * 获得高
	 * @return
	 */
	public int getVideoHeight() {
		return player.getVideoHeight();
	}
	
	/**
	 * 快进到...
	 * @return
	 */
	public void seekTo(int msec) {
		player.seekTo(msec);
	}
	
	/**
	 * 播放视频时是否常量
	 * @return
	 */
	public void setScreenOnWhilePlaying(boolean screenOn) {
		player.setScreenOnWhilePlaying(screenOn);
	}
	
	
	
	/**
	 * 获取MediaPlayer, 以便更多的控制视频的播放, 包括开始,暂停,停止等等
	 * @return
	 */
	public MediaPlayer getMediaPlayer() {
		return player;
	}	

}
