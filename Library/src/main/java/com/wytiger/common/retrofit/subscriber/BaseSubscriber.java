package com.wytiger.common.retrofit.subscriber;


import com.wytiger.common.retrofit.exception.AppBizTokenExpiredException;
import com.wytiger.common.retrofit.exception.AppEmptyDataException;
import com.wytiger.common.retrofit.exception.AppHttpException;
import com.wytiger.common.retrofit.exception.WalletAccountLockedException;
import com.wytiger.common.retrofit.exception.WalletLoginAtOtherPlaceException;
import com.wytiger.common.utils.common.LogUtil;
import com.wytiger.common.utils.common.NetUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: wujiejiang
 * Time: 2016/6/28
 * Desc: 所有网络请求Subscriber基类
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        //默认Toast全局显示HttpException和WalletHttpException
        //业务层一般不需要重载onError，只需要重载自己需要处理的异常，处理完返回true;未处理返回false，交给BaseSubscriber处理。
        if (e instanceof HttpException) {
            LogUtil.e("httplog", e.getMessage());
            if (!handleHttpException((HttpException)e)) {

            }
        } else if (e instanceof AppHttpException) {
            if (!handleWalletHttpException((AppHttpException)e)) {

            }
        } else if (e instanceof AppEmptyDataException){
            //空数据异常都是由业务层处理
            handleWalletEmptyDataException((AppEmptyDataException)e);
        } else if (e instanceof AppBizTokenExpiredException){
            //WalletBusiTokenExpiredException统一由WalletBusiTokenExpiredHandler进行统一处理，不需要业务层单独处理
            // do nothing
        } else if (e instanceof WalletLoginAtOtherPlaceException) {
            //WalletLoginAtOtherPlaceException统一由全局WalletApplication处理，不需要业务层单独处理
            // do nothing
        } else if (e instanceof WalletAccountLockedException) {
            //默认是不需要做任何处理的，application中会处理并且退出登录
            //但是登录时还是需要提示异常信息的
            handleWalletAccountLockedException((WalletAccountLockedException)e);
        } else {
            LogUtil.e("httplog", "msg = " + e.getMessage() + ", cause = " + e.getCause());
            e.printStackTrace();

            //ConnectException ： 指的是服务器请求超时,当一笔交易，如果请求失败，那可以允许用户再次提交(待加入自动重试功能？)
            //SocketTimeoutException:指的是服务器响应超时,如果是响应失败，那就说明用户提交成功了，应该防止用户再次提交
            if (e instanceof ConnectException
                    || e instanceof SocketTimeoutException) {
                handleConnectException(e);
                //直接返回,在异步任务有结果之后再调用onPostError，此时在onPostError中再关闭进度对话框等操作
                return;
            } else {
                if (!handleOtherException(e)) {

                }
            }
        }

        onPostError(e);
    }

    @Override
    public void onNext(T t) {

    }

    /**
     * 通用的http异常
     * @param httpException
     * @return
     */
    public boolean handleHttpException(HttpException httpException) {
        return false;
    }

    /**
     * http业务异常
     * @param appHttpException
     * @return
     */
    public boolean handleWalletHttpException(AppHttpException appHttpException) {
        return false;
    }

    /**
     * 空数据异常
     * @param appEmptyDataException
     * @return
     */
    public boolean handleWalletEmptyDataException(AppEmptyDataException appEmptyDataException) {
        return false;
    }

    public boolean handleWalletAccountLockedException(WalletAccountLockedException walletAccountLockedException) {
        return false;
    }

    /**
     * 以上所以异常之外的异常,包括ConnectException,SocketTimeoutException等
     *
     * @return
     */
    public boolean handleOtherException(Throwable e) {
        return false;
    }

    /**
     * 出错之后的统一处理，例如可以关闭loadingDialog
     * @param e
     * @return
     */
    public void onPostError(Throwable e) {
    }

    private void handleConnectException(Throwable e) {
        Observable.create(subscriber -> {
             boolean result = NetUtil.ping();
            if (result) {
                subscriber.onNext(result);
                subscriber.onCompleted();
            } else {
                subscriber.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    //如果ping得通，统一提示服务器开小差

                    onPostError(e);
                }, e2 -> {
                    //如果ping不通,提示网络异常,请检查网络设置
                    onPostError(e2);
                });
    }
}
