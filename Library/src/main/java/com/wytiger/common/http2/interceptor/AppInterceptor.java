package com.wytiger.common.http2.interceptor;


import com.wytiger.common.gloable.AppApplication;
import com.wytiger.common.http2.AppHttpUtil;
import com.wytiger.common.http2.IAppService;
import com.wytiger.common.http2.params.BaseParams;
import com.wytiger.common.http2.params.RiskParams;
import com.wytiger.common.utils.common.LogUtil;
import com.wytiger.common.utils.common.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: wujiejiang
 * Time: 2016/6/27
 * Desc: 拦截器
 */
public class AppInterceptor implements Interceptor {
    private static final String TAG = "httplog";
    private static final String RISK_PARAMS_KEY = "limitParams";
    private static final String BIZ_PARAMS_KEY = "reqParams";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String responseType = originalRequest.header(IAppService.RESPONSE_TYPE_KEY);
        String serviceType = originalRequest.header(IAppService.SERVICE_TYPE_KEY);

        if (IAppService.RESPONSE_TYPE_MOCK.equals(responseType)) {
            //MOCK数据
//            return WalletMockService.mockResponse(originalRequest);
            return null;
        } else {
            //网络请求数据

            //判断当前网络情形，如果没有网络，不发送网络请求
            if (!NetUtil.isConnected(AppApplication.getInstance())) {
                return AppHttpUtil.buildNoNetworkResponse(originalRequest);
            }

            //下载服务不需要拦截加密处理
            if (IAppService.SERVICE_TYPE_DOWNLOAD.equals(serviceType)) {
                LogUtil.d(TAG, "Sending request:"
                        + "(url=" + originalRequest.url().toString() + ");");
                long startTime = System.currentTimeMillis();
                Response response= chain.proceed(originalRequest);
                long endTime = System.currentTimeMillis();
                LogUtil.d(TAG, "Received response from:"
                        + "(url=" + originalRequest.url().toString() + ")" + " in " + (endTime-startTime) + " ms");
                return  response;
            }

            //普通通讯不需要拦截加密处理
//            if ("0".equals(AppHttpUtil.getSecurityLevel(originalRequest.url().toString()))) {
//                return chain.proceed(originalRequest);
//            }

            //从网络获取数据
            Request newRequest = interceptRequest(originalRequest);

            long startTime = System.currentTimeMillis();
            Response response = null;
            try {
                response = chain.proceed(newRequest);
            } catch (Exception e) {
                //RealCall.getResponse中engine.sendRequest()会抛出异常,如果sendRequest抛出异常,
                //将获取不到response信息，所以将异常重新抛出给业务层(BaseSubscriber)处理
                throw e;
            }
            long endTime = System.currentTimeMillis();

            LogUtil.d(TAG, "Received response from:"
                    + "(url=" + newRequest.url().toString() + ")" + " in " + (endTime-startTime) + " ms");
            //注：response.body().string()只能调用一次
            //        + "response=" + response.body().string());

            return response;
        }

    }

    private Request interceptRequest(Request oldRequest) {
        String[] allParams = getAllParams(oldRequest);

//        String content = WalletEncryptUtils.encrypt(allParams);
        String content =allParams.toString();

        RequestBody newBody = new FormBody.Builder()
                .add("params", content)
                .build();

        //MediaType mediaType = oldRequest.body().contentType();
        //RequestBody newBody = RequestBody.create(mediaType, content);

        Request.Builder builder = oldRequest.newBuilder();
        builder.method(oldRequest.method(), newBody);
        Request newRequest = builder.build();

        LogUtil.d(TAG, "Sending request:"
                + "(url=" + newRequest.url().toString() + ");"
                + "content=" + logAllParams(allParams));

        return newRequest;
    }

    private String logAllParams(String[] allParams) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : allParams) {
            stringBuilder.append(string).append(";");
        }
        return stringBuilder.toString();
    }

    private String[] getAllParams(Request originalRequest) {
        List<String> allParams = new ArrayList<>();
        //基本参数不拼接
        allParams.addAll(getBaseParams(originalRequest).toStringList());

        //风控参数字典排序以&符号拼接
        allParams.add(RISK_PARAMS_KEY + "=" + getRiskParams(originalRequest).toEssentialString());

        //业务参数字典排序以&符号拼接
        allParams.add(BIZ_PARAMS_KEY + "=" + getReqParams(originalRequest));

        String[] tempParams = new String[allParams.size()];
        return allParams.toArray(tempParams);
    }

    /**
     * 排序拼接业务请求参数
     *
     * @param originalRequest
     * @return
     */
    private String getReqParams(Request originalRequest) {
        List<String> reqParams = getOriginalReqParams(originalRequest);

        Collections.sort(reqParams);

        StringBuilder builder = new StringBuilder();
        for (String param : reqParams) {
            builder.append(param).append("&");
        }

        //删除最后一个"&"
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private List<String> getOriginalReqParams(Request originalRequest) {
        List<String> reqParams = new ArrayList<>();
        RequestBody body = originalRequest.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); ++i) {
                reqParams.add(formBody.name(i) + "=" + formBody.encodedValue(i));
            }
        }

        return reqParams;
    }

    /**
     * 获取并设置基本参数
     *
     * @param originalRequest
     * @return
     */
    private BaseParams getBaseParams(Request originalRequest) {
        BaseParams baseParams = BaseParams.newInstance();
        //baseParams.setService(AppHttpClient.getServiceName(originalRequest.url().toString()));
        baseParams.setVersion(AppHttpUtil.getServiceVersion(originalRequest.url().toString()));
        baseParams.setSecurityLevel(AppHttpUtil.getSecurityLevel(originalRequest.url().toString()));

        return baseParams;
    }

    /**
     * 获取并设定风控参数
     *
     * @param originalRequest
     * @return
     */
    private RiskParams getRiskParams(Request originalRequest) {
        RiskParams riskParams = null;

        return riskParams;
    }
}
