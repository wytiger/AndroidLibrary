//package com.wytiger.common.http2.subscriber;
//
//
//import com.wytiger.common.http2.exception.AppEmptyDataException;
//
//import java.lang.ref.WeakReference;
//
///**
// * Author: wujiejiang
// * Time: 2016/12/20
// * Desc: 包含LoadingLayout界面，通用的Subscriber
// * 界面退出时，一定要将Subscriber解除，调用unsubscribe
// */
//public class LoadingLayoutSubscriber<T> extends BaseSubscriber<T>{
//    protected WeakReference<LoadingLayoutManager> loadingLayoutManager;
//
//    public LoadingLayoutSubscriber(LoadingLayoutManager loadingLayoutManager) {
//        this.loadingLayoutManager = new WeakReference<>(loadingLayoutManager);
//    }
//
//    @Override
//    public boolean handleWalletEmptyDataException(AppEmptyDataException appEmptyDataException) {
//        if (null != loadingLayoutManager
//                && loadingLayoutManager.get() != null) {
//            loadingLayoutManager.get().showEmptyResult();
//        }
//        return true;
//    }
//
//    @Override
//    public void onPostError(Throwable e) {
//        //WalletEmptyDataException界面与其他异常展示界面不一样
//        //数据为空的界面已通过重载handleWalletEmptyDataException处理
//        if (e instanceof AppEmptyDataException) {
//            return;
//        }
//        if (null != loadingLayoutManager
//                && loadingLayoutManager.get() != null) {
//            loadingLayoutManager.get().showHttpExceptionResult();
//        }
//    }
//}
