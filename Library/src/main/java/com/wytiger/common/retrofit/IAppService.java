package com.wytiger.common.retrofit;


import com.wytiger.common.retrofit.response.AppResponse;
import com.wytiger.common.retrofit.response.BaseResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Author: wujiejiang
 * Time: 2016/6/23
 * Desc: 钱包所有网络请求
 * <p>
 * 注：
 * 1.@Header(RESPONSE_TYPE_KEY) String responseType：该参数为自定义Header，该Header控制该网络请求返回的数据是否是Mock假数据，测试专用
 * 2.@Header(SERVICE_TYPE_KEY) String serviceType：该参数为自定Header,该Header控制该网络请求是否需要显示进度条；
 * 3.@POST请求要求至少有一个@Field参数，如果没有参数，使用@GET请求
 * 4.当服务器返回BaseResponse时方法返回Observable<BaseResponse>, 返回WalletResponse时非法返回Observable<AppResponse>
 * 如果没有网络时，是否需要弹出”没有网络“的提示，后台的请求都是不需要弹出提示的。
 */
public interface IAppService {
    public static final String RESPONSE_TYPE_KEY = "Wallet-response-type";
    public static final String RESPONSE_TYPE_MOCK = "mock";
    public static final String RESPONSE_TYPE_NET = "net";

    public static final String SERVICE_TYPE_KEY = "Wallet-service-type";
    public static final String SERVICE_TYPE_FOREGROUND = "foreground";
    public static final String SERVICE_TYPE_BACKGROUND = "background";
    public static final String SERVICE_TYPE_DOWNLOAD = "download";//下载服务

    //单点登录
    @POST("/app/auth/toLogin.htm")
    @FormUrlEncoded
    Observable<AppResponse> login(@Field("userName") String userName, @Field("password") String password, @Field("oldPassword") String oldPassword, @Field("pushId") String pushId);
    //忘记密码获取短信验证码
    @POST("/app/auth/getVerifyCodeForReset.htm")
    @FormUrlEncoded
    Observable<BaseResponse> getVerifyCodeForReset(@Field("mobile") String mobile, @Field("busiToken") String busiToken);

    //文件上传
    @Multipart
    @POST("/app/file/upload")
    Observable<AppResponse> uploadFileWithParts(@Part() List<MultipartBody.Part> parts);


}
