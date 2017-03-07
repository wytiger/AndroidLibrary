package com.wytiger.common.retrofit.exception;


import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action0;

/**
 * Author: wujiejiang
 * Time: 2016/10/10
 * Desc:
 */
public class WalletExceptionHandler {
    private static WalletExceptionHandler sInstance;
    private WalletExceptionHandler() {
    }
    public static WalletExceptionHandler getInstance() {
        if (null == sInstance) {
            synchronized (WalletExceptionHandler.class) {
                if (null == sInstance) {
                    sInstance = new WalletExceptionHandler();
                }
            }
        }

        return sInstance;
    }

    public void handleHttpException(HttpException httpException) {
        //所有的网络异常暂时用默认的提示
        //TODO 根据不同的错误码返回的不同的提示信息
//        WalletToast.show(httpException.getMessage());
    }

    public void handleWalletHttpException(AppHttpException appHttpException, Action0 walletHttpExceptionAction) {
        //默认是弹出后台返回的内部错误类型信息retInfo，当walletHttpExceptionAction不为空时，交给业务层处理
        if (null != appHttpException) {
            walletHttpExceptionAction.call();
        } else {
//            WalletToast.show(appHttpException.getMessage());
        }
    }

}
