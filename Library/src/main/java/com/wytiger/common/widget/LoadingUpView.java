package com.wytiger.common.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytiger.common.R;
import com.wytiger.common.utils.StringUtil;


/**
 * 加载等待框 此等待框基于单个Activity内部显示
 *
 * @version 2012-11-1 下午1:31:10 xu.xb
 */
public class LoadingUpView {
    private static final int START_ANIM = 3;
    private static final int ERROR = 4;
    private static final int DISMISS = 5;
    private static final int SHOW_DIALOG = 6;
    private LinearLayout mLoadingView;
    private Animation mAnim;
    private ImageView mIvLoading;
    private TextView mTvMsg;
    private Activity mCurrentActivity;
    private boolean mIsShowing;
    private View mParentView;
    private ProgressDialog mProgressDialog;
    private boolean mIsFrameLayoutParentView;
    private int mScreenWidth;
    private int mScreenHeight;
    private boolean mIsBlock;
    private boolean mCancelable;
    private boolean mCancelCloseActivity;
    private int mResId;

    /**
     * 构造方法
     *
     * @param activity Activity
     * @param isBlock  是否阻塞用户操作
     */
    public LoadingUpView(Activity activity, boolean isBlock) {
        this.mIsBlock = isBlock;
        init(activity);
    }

    public LoadingUpView(Activity activity) {
        init(activity);
    }

    private void init(Activity activity) {
        this.mCurrentActivity = activity;
        if (activity.getWindow() == null) {
            return;
        }
        mParentView = activity.getWindow().getDecorView().getRootView();
        mIsFrameLayoutParentView = mParentView instanceof FrameLayout;
    }

    private void initView() {
        mLoadingView = (LinearLayout) View.inflate(mCurrentActivity, R.layout.popup, null);
        mTvMsg = (TextView) mLoadingView.findViewById(R.id.tv_popup);
        mIvLoading = (ImageView) mLoadingView.findViewById(R.id.img_popup);
        if (0 != mResId) {
            mIvLoading.setImageResource(mResId);
        }
        mAnim = AnimationUtils.loadAnimation(mCurrentActivity, R.anim.popup_loading);
    }

    public void setLoadView(int resId) {
        mResId = resId;
    }

    public void showPopup() {
        showPopup("");
    }

    public void showPopup(final String msg) {
        if (mCurrentActivity != null && mCurrentActivity.isFinishing()) {
            return;
        }
        if (mIsBlock) {
            changeShowStatus(true);
            changeStatus(SHOW_DIALOG, msg);
        } else {
            if (mParentView == null) {
                return;
            }
            if (mLoadingView == null) {
                initView();
            }
            removeLoadingView();
            addLoadingView(msg);
        }

    }

    public void dismiss() {
        if (mIsShowing) {
            changeShowStatus(false);
            changeStatus(DISMISS);
        }
    }

    /**
     * 设置是否阻塞用户操作，在使用showPopup方法前调用
     *
     * @param isBlock 是否阻塞用户操作
     */
    public void setIsBlock(boolean isBlock) {
        this.mIsBlock = isBlock;
    }

    /**
     * 是否按返回键可以消失，使用阻塞时设置此参数
     *
     * @param mCancelable 是否按返回键可以消失
     */
    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;
    }

    /**
     * 是否按返回键关闭Activity(show之前调用)
     *
     * @param cancelCloseActivity 是否关闭Activity
     */
    public void setCancelCloseActivity(boolean cancelCloseActivity) {
        this.mCancelCloseActivity = cancelCloseActivity;
    }

    private void removeLoadingView() {
        if (mIsFrameLayoutParentView) {
            ((FrameLayout) mParentView).removeView(mLoadingView);
        }
    }

    private void addLoadingView(String msg) {
        if (mIsFrameLayoutParentView) {
            if (mScreenWidth == 0) {
                getDisplayConfig();
            }
            ((FrameLayout) mParentView)
                    .addView(mLoadingView, new FrameLayout.LayoutParams(mScreenWidth, mScreenHeight));
            mIvLoading.setVisibility(View.VISIBLE);
            if (StringUtil.isNullOrEmpty(msg)) {
                mTvMsg.setVisibility(View.GONE);
            } else {
                mTvMsg.setVisibility(View.VISIBLE);
                mTvMsg.setText(msg);
            }
            changeShowStatus(true);
            changeStatus(START_ANIM);
        }
    }

    public void onResume() {
        if (mIsShowing && mLoadingView != null) {
            changeStatus(START_ANIM);
        }
    }

    /**
     * 返回EvtLoadingUpView是否显示
     *
     * @return EvtLoadingUpView是否显示
     */
    public boolean isShowing() {
        return mIsShowing;
    }

    private void changeShowStatus(boolean bShowing) {
        this.mIsShowing = bShowing;
    }

    /**
     * showProgressDialog 创建显示
     *
     * @param act Activity
     * @param msg 消息
     */
    private void showProgressDialog(final Activity act, String msg) {
        Activity activity = act;
        if (act.isFinishing()) {
            return;
        }
        if (act.getParent() != null) {
            activity = act.getParent();
        }
        if (activity.getParent() != null) {
            activity = activity.getParent();
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 如果退出要关闭Activity，就不能设置是否取消，一定可以取消
            if (mCancelCloseActivity) {
                mProgressDialog.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface arg0) {
                        if (!act.isFinishing()) {
                            act.finish();
                        }
                    }
                });
            }
            // 在show之前判断是否已经关闭
            if (activity.isFinishing()) {
                return;
            }
            mProgressDialog.show();
            View view = View.inflate(activity, R.layout.popup, null);
            view.setBackgroundColor(Color.TRANSPARENT);
            ImageView mIvLoading = (ImageView) view.findViewById(R.id.img_popup);
            TextView mLoadMsg = (TextView) view.findViewById(R.id.tv_popup);

            if (0 != mResId) {
                mIvLoading.setImageResource(mResId);
            }
            mIvLoading.setVisibility(View.VISIBLE);
            if (mAnim == null) {
                mAnim = AnimationUtils.loadAnimation(mCurrentActivity, R.anim.popup_loading);
            }
            mAnim.setInterpolator(new LinearInterpolator());
            mIvLoading.startAnimation(mAnim);
            if (StringUtil.isNullOrEmpty(msg)) {
                mLoadMsg.setVisibility(View.GONE);
            } else {
                mLoadMsg.setVisibility(View.VISIBLE);
                mLoadMsg.setText(msg);
            }
            Window window = mProgressDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1f;
            lp.dimAmount = 0.000001f; // 修改在2.1和2.2的模拟器上黑屏的问题
            window.setAttributes(lp);
            window.setContentView(view);

            mProgressDialog.setIndeterminate(true);
            // 如果退出要关闭Activity，就不能设置是否取消，一定可以取消
            if (!mCancelCloseActivity) {
                mProgressDialog.setCancelable(mCancelable);
            } else {
                mProgressDialog.setCancelable(true);
            }
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
    }

    private void getDisplayConfig() {
        DisplayMetrics dm = new DisplayMetrics();
        mCurrentActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        Rect frame = new Rect();
        mCurrentActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    }

    private void changeStatus(int status) {
        changeStatus(status, "");
    }

    private void changeStatus(int status, String info) {
        switch (status) {
            case START_ANIM:
                if (mIvLoading != null) {
                    mIvLoading.startAnimation(mAnim);
                }
                break;
            case ERROR:
                break;
            case DISMISS:
                if (mIsBlock) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                } else {
                    removeLoadingView();
                    mLoadingView = null;
                }
                break;
            case SHOW_DIALOG:
                showProgressDialog(mCurrentActivity, info);
                break;
            default:
                break;
        }

    }
}
