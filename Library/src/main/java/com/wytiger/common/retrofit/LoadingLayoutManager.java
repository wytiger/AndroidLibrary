package com.wytiger.common.retrofit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytiger.common.R;


/**
 * Author: xiongqi
 * Time: 2016/9/24
 * Desc:
 */
public class LoadingLayoutManager {
    private View rootView;
    private View loadingLayout;
    private View loadedResultLayout;
    private ImageView loadedResultImageView;
    private TextView loadedResultTextView;
    private boolean isHttpException = false;

    public LoadingLayoutManager(View rootView) {
        this.rootView = rootView;
        loadingLayout = rootView.findViewById(R.id.ll_loading);
        loadedResultLayout = rootView.findViewById(R.id.ll_loaded_result);
        loadedResultImageView = (ImageView) rootView.findViewById(R.id.iv_loaded_result);
        loadedResultTextView = (TextView) rootView.findViewById(R.id.tv_loaded_result);

        loadedResultLayout.setOnClickListener(v -> {
            if (null != requestRetryListener && isHttpException) {
                showLoadingLayout();
                requestRetryListener.call();
            }
        });
    }


    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadedResultLayout.setVisibility(View.GONE);
    }

    public void showLoadedResultLayout() {
        rootView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        loadedResultLayout.setVisibility(View.VISIBLE);
        isHttpException = false;
    }

    /**
     * 隐藏整个布局界面
     */
    public void hideLoadingLayout() {
        rootView.setVisibility(View.GONE);
        isHttpException = false;
    }

    public void showLoadedResult(int loadedResultImageResId, int loadedResultTextResId) {
        showLoadedResultLayout();
        loadedResultImageView.setImageResource(loadedResultImageResId);
        loadedResultTextView.setText(loadedResultTextResId);
    }

    public void showLoadedResult(int loadedResultTextResId) {
        showLoadedResultLayout();
        loadedResultTextView.setText(loadedResultTextResId);
    }

    public void showLoadedResult(String loadedResultText) {
        showLoadedResultLayout();
        loadedResultTextView.setText(loadedResultText);
    }

    /**
     * 默认空数据界面
     */
    public void showEmptyResult() {
        showLoadedResultLayout();
        loadedResultTextView.setText("暂无数据");
        loadedResultImageView.setImageResource(R.drawable.image_empty_data);
    }

    public void showEmptyResult(int loadedResultTextResId) {
        showLoadedResultLayout();
        loadedResultTextView.setText(loadedResultTextResId);
        loadedResultImageView.setImageResource(R.drawable.image_empty_data);
    }

    /**
     * 默认网络异常界面
     */
    public void showHttpExceptionResult() {
        showLoadedResultLayout();
        isHttpException = true;
        loadedResultTextView.setText("轻触屏幕，重新加载");
        loadedResultImageView.setImageResource(R.drawable.image_network_exception);
    }

    public interface RequestRetryListener {
        void call();
    }
    private RequestRetryListener requestRetryListener;
    public void setRequestRetryListener(RequestRetryListener requestRetryListener) {
        this.requestRetryListener = requestRetryListener;
    }
}
