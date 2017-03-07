//package com.wytiger.common.http2;
//
//import android.app.Activity;
//import android.text.TextUtils;
//
//import com.wytiger.common.R;
//import com.wytiger.common.http2.exception.AppBizTokenExpiredException;
//import com.wytiger.common.http2.exception.AppHttpException;
//import com.wytiger.common.http2.subscriber.BaseSubscriber;
//import com.wytiger.common.utils.common.LogUtil;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//
//import retrofit2.http.Field;
//import rx.Observable;
//import rx.functions.Func1;
//
///**
// * Author: wujiejiang
// * Time: 2016/8/31
// * Desc: 业务Token过期自动重试机制:
// * 当业务Token过期时,将loginToken发送到后台重新验证获取busiToken。获取成功之后重新发送失败的请求
// * 如果loginTokne验证失败，跳转到登录界面
// */
//public class AppBizTokenExpiredHandler implements InvocationHandler {
//    private static final String TAG = AppBizTokenExpiredHandler.class.getSimpleName();
//    //更新业务token异常
//    private Throwable updateTokenError = null;
//
//    private Object proxyObject;
//
//    public AppBizTokenExpiredHandler(Object proxyObject) {
//        this.proxyObject = proxyObject;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        return Observable.just(null).flatMap(new Func1<Object, Observable<?>>() {
//            @Override
//            public Observable<?> call(Object o) {
//                Observable<?> observable = null;
//                try {
//                    observable = (Observable<?>) method.invoke(proxyObject, args);
//                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                    LogUtils.e("AppBizTokenExpiredHandler", "WalletBusiTokenExpiredHandler动态代理异常, e = " + e.getMessage() + ", cause = " + e.getCause());
//                    e.printStackTrace();
//                }
//                return observable;
//            }
//        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
//            @Override
//            public Observable<?> call(Observable<? extends Throwable> observable) {
//                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Throwable throwable) {
//                        if (throwable instanceof AppBizTokenExpiredException) {
//                            return refreshBusiTokenWhenTokenExpired(method, args);
//                        }
//                        return Observable.error(throwable);
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 如果业务Token过期，重新登录获取业务Token
//     *
//     * @return Observable
//     */
//    private Observable<?> refreshBusiTokenWhenTokenExpired(Method method, Object[] args) {
//        synchronized (AppBizTokenExpiredHandler.class) {
//            LogUtil.d(TAG, "method " + method.getName() + " expired");
//
//            BaseSubscriber baseSubscriber = new BaseSubscriber<HashMap<String, Object>>() {
//                @Override
//                public void onCompleted() {
//                    super.onCompleted();
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    //super.onError(e);
//                    LogUtil.e(TAG, e.getMessage() + "");
//                    updateTokenError = e;
//                    if (e instanceof AppHttpException) {
//                        if (ServerErrorCodes.ERR_LOGIN_TOKEN_EXPIRE.getErrorCode().equals(((AppHttpException) e).getResultCode())
//                                || ServerErrorCodes.ERR_LOGIN_TOKEN.getErrorCode().equals(((AppHttpException) e).getResultCode())) {
//                            //跳转到登录界面
//                            try {
//                                Activity currentActivity = WalletActivityManager.getInstance().getCurrentActivity();
//                                currentActivity.runOnUiThread(() -> {
//                                    WalletToast.show(R.string.login_token_expired);
//                                    LoginHelper.logoutOffline(()
//                                            -> HomeActivity.startHomeActivity(currentActivity, AppBizTokenExpiredHandler.class.getSimpleName()));
//                                });
//                            } catch (Exception ee) {
//                                ee.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onNext(HashMap<String, Object> hashMap) {
//                    super.onNext(hashMap);
//                    if (null != hashMap) {
//                        LoginHelper.autoLoginSuccess(hashMap);
//
//                        updateMethodToken(method, args);
//                    }
//
//                    LogUtil.d(TAG, "login again and method " + method.getName() + " updated");
//                }
//            };
//
//            //TODO 待优化，如果同时多个请求busiTokne失效，只需要验证一次loginToken即可
//            RetrofitManager.getInstance().getWalletService().loginAuto(UserProfileService.getCurrentUserLoginTokenSync())
//                    .map(new AppHttpClient.HttpResult2ObjectFunc())
//                    .subscribe(baseSubscriber);
//
//            if (updateTokenError != null) {
//                return Observable.error(updateTokenError);
//            } else {
//                return Observable.just(true);
//            }
//        }
//
//    }
//
//    /**
//     * Update the token of the args in the method.
//     */
//    private void updateMethodToken(Method method, Object[] args) {
//        if (!TextUtils.isEmpty(UserInfoCache.getInstance().busiToken)) {
//            Annotation[][] annotationsArray = method.getParameterAnnotations();
//            Annotation[] annotations;
//            if (annotationsArray != null && annotationsArray.length > 0) {
//                for (int i = 0; i < annotationsArray.length; i++) {
//                    annotations = annotationsArray[i];
//                    for (Annotation annotation : annotations) {
//                        if (annotation instanceof Field) {
//                            if ("busiToken".equals(((Field) annotation).value())) {
//                                args[i] = UserInfoCache.getInstance().busiToken;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
