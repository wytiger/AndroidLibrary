package com.wytiger.common.http2;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.wytiger.common.R;
import com.wytiger.common.constants.AppConstants;
import com.wytiger.common.gloable.AppApplication;
import com.wytiger.common.http2.response.BaseResponse;
import com.wytiger.common.utils.JsonUtils;
import com.wytiger.common.utils.common.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author: wujiejiang
 * Time: 2016/7/13
 * Desc:
 */
public class AppHttpUtil {
    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    public static <T> Response buildCustomerResponse(Request request, T customerResponse) {
        Response.Builder builder = new Response.Builder();

        MediaType mediaType = MediaType.parse(MEDIA_TYPE_JSON);
        String content = JsonUtils.toJson(customerResponse);
        ResponseBody responseBody = ResponseBody.create(mediaType, content);
        builder.body(responseBody);

        return buildLegalResponseParameter(request, builder).build();

    }

    public static Response buildNoNetworkResponse(Request request) {
        Response.Builder builder = new Response.Builder();

        MediaType mediaType = MediaType.parse(MEDIA_TYPE_JSON);
        NoNetworkResponse response = NoNetworkResponse.newInstance();
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("result", response.getResult());
        jsonContent.put("retinfo", response.getRetinfo());
        jsonContent.put("data", response.getData());
        String content = jsonContent.toJSONString();
        ResponseBody responseBody = ResponseBody.create(mediaType, content);
        builder.body(responseBody);
        return buildLegalResponseParameter(request, builder).build();
    }

    private static class NoNetworkResponse extends BaseResponse {
        private String data;

        public String getData() {
            return data;
        }

        public static NoNetworkResponse newInstance() {
            NoNetworkResponse response = new NoNetworkResponse();
            response.setResult(AppConstants.NO_NETWORK_CODE);
            response.setRetinfo(AppApplication.getInstance().getString(R.string.no_network_now));
            response.data = "";

            return response;
        }
    }


    public static Response.Builder buildLegalResponseParameter(Request request, Response.Builder builder) {
        //设定以下三个合法值，builder.build中会校验这三个值
        builder.request(request);
        builder.protocol(Protocol.HTTP_1_1);
        builder.code(200);

        return builder;
    }

    /**
     * 把File对象转化成MultipartBody
     *
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * 把File转化成MultipartBody.Part
     *
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


    /**
     * 根据url获取该接口对应的名称，每一个接口都必须在case语句中增加url和对应的接口名称
     *
     * @param url
     * @return
     */
    public static String getServiceName(String url) {
        String serviceName = "";

        if (TextUtils.isEmpty(url)) {
            LogUtil.e("url can not be null");
            return null;
        }

        switch (url) {
            //测试接口
            case "https://172.31.148.21/app/auth/toLogin":
                serviceName = "login";
                break;
        }

        return serviceName;
    }

    /**
     * 根据url获取该接口对应的版本号，默认版本号"2.0.0.0"
     * 如果需要修改特定接口的版本号，增加case语句
     *
     * @param url
     * @return
     */
    public static String getServiceVersion(String url) {
        String serviceVersion = "2";

        switch (url) {

            default:
                serviceVersion = "2";
                break;
        }

        return serviceVersion;
    }

    public static String getSecurityLevel(String url) {
        String securityLevel = "1";

        switch (url) {
            default:
                securityLevel = "1";
                break;
        }

        return securityLevel;
    }
}
