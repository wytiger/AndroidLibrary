package com.wytiger.common.retrofit;

import com.wytiger.common.retrofit.response.AppResponse;
import com.wytiger.common.retrofit.response.BaseResponse;
import com.wytiger.common.utils.FastJsonUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: wujiejiang
 * Time: 2016/6/23
 * Desc:网络请求统一接口。
 * 当返回BaseResponse时（无data数据项）无需map(new HttpResult2ObjectFunc())映射，当返回WalletResponse时（有data数据项）需要map映射
 *
 * MVP架构的理解
 * 1：Model层获取数据，并处理业务逻辑，Model层和Presenter层双向交互，这是当前APP的采取的方式，但是该方式有缺陷：
 * Model层获取数据并处理（业务逻辑），需要通过接口回调给Presenter，要增加太多这种回调的接口。
 *
 * 2：Model层获取数据，交给Presenter层处理（业务逻辑），Model层和Presenter层为单向交互，由Presenter主动调用Model层获取数据。
 *
 * 无论采用哪种实现方式，WalletHttpClient类应该不存在，而是被分拆成不同的Model，例如LoginModel，LoginBiz和LoginBizImpl都不需要；获取数据的Subscriber应该在
 * Presenter中创建。
 *
 */
public class AppHttpClient {
    private static final String TAG = AppHttpClient.class.getSimpleName();

    private AppHttpClient() {
    }

    /**
     * 单点登录
     *
     * @param subscriber
     * @param userName
     * @param password
     * @param pushId     激光推送id
     * @return
     */
    public static Subscription login(Subscriber<HashMap<String, Object>> subscriber, String userName, String password, String oldPassword, String pushId) {
        return RetrofitManager.getInstance().getWalletService().login(userName, password, oldPassword, pushId)
                .map(new HttpResult2ObjectFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    /**
     * 用户身份认证时发送短信验证码
     *
     * @param mobile
     * @param subscriber
     * @return
     */
    public static Subscription sendSmsForCheckIdentity(String mobile, String busiToken, Subscriber<BaseResponse> subscriber) {
        return RetrofitManager.getInstance().getWalletService().getVerifyCodeForReset(mobile, busiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //单线程上传多个文件
    public static Subscription uploadFiles(Subscriber<HashMap<String, Object>> subscriber, List<File> files) {
        return RetrofitManager.getInstance().getWalletService().uploadFileWithParts(AppHttpUtil.filesToMultipartBodyParts(files))
                .map(new HttpResult2ObjectFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    /**
     * 用来统一处理Http的resultCode,并将WalletResponse的data部分剥离出来返回给subscriber
     */
    public static class HttpResult2ObjectFunc implements Func1<AppResponse, HashMap<String, Object>> {

        @Override
        public HashMap<String, Object> call(AppResponse response) {
            return FastJsonUtil.fromJson2ObjectHashMap(response.getData());
        }
    }
}
