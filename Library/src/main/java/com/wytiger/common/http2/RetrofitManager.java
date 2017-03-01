package com.wytiger.common.http2;


import com.wytiger.common.BuildConfig;
import com.wytiger.common.config.AppConfig;
import com.wytiger.common.constants.AppEnvironment;
import com.wytiger.common.http2.converter.AppConverterFactory;
import com.wytiger.common.http2.interceptor.AppInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Author: wujiejiang
 * Time: 2016/6/23
 * Desc:
 */
public class RetrofitManager {

//     private static final String BASE_URL = AppEnvironment.DEVELOPER_ENVIRONMENT == AppApplication.environment
//            ? "https://wallet.pay1pay.com"
//            : "https://wallet.pay1pay.com";


    private static final String BASE_URL = AppEnvironment.DEVELOPER_ENVIRONMENT == AppConfig.environment
            ? "http://58.252.101.221"
            : "http://58.252.101.221";

    /**
     * 连接超时时间30s
     */
    private static final long CONNECT_TIMEOUT = 30 * 1000;
    /**
     * 读超时时间60s
     */
    private static final long READ_TIMEOUT = 60 * 1000;
    /**
     * 写超时时间60s
     */
    private static final long WRITE_TIMEOUT = 60 * 1000;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private IAppService walletService;

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public IAppService getWalletService() {
        return walletService;
    }

    private static RetrofitManager sInstance;

    public static RetrofitManager getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitManager.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitManager();
                }
            }
        }

        return sInstance;
    }

    private RetrofitManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())//网络请求监控
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new AppInterceptor());

        //Debug模式下信任所有证书
        if (BuildConfig.DEBUG) {
            builder.sslSocketFactory(AppSSLSocketFactory.createSSLSocketFactory())
                    .hostnameVerifier(new AppSSLSocketFactory.TrustAllHostnameVerifier());
        }

        okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(AppConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

//        IAppService tempService = retrofit.create(IAppService.class);
//        walletService = (IAppService) Proxy.newProxyInstance(IAppService.class.getClassLoader(),
//                new Class<?>[]{IAppService.class}, new AppBizTokenExpiredHandler(tempService));
        walletService = retrofit.create(IAppService.class);
    }
}
