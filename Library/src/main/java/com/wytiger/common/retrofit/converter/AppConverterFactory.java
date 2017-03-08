package com.wytiger.common.retrofit.converter;

import android.text.TextUtils;

import com.wytiger.common.R;
import com.wytiger.common.constants.AppConstants;
import com.wytiger.common.gloable.AppApplication;
import com.wytiger.common.retrofit.exception.AppEmptyDataException;
import com.wytiger.common.retrofit.exception.AppHttpException;
import com.wytiger.common.retrofit.response.AppResponse;
import com.wytiger.common.retrofit.response.BaseResponse;
import com.wytiger.common.retrofit.response.FileResponse;
import com.wytiger.common.utils.JsonFormatUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Author: wujiejiang
 * Time: 2016/7/12
 * Desc:
 */
public class AppConverterFactory extends Converter.Factory{
    private static final String TAG = AppConverterFactory.class.getSimpleName();
    private static final String RESPONSE_DATA_EMPTY = "{}";

    public static AppConverterFactory create() {
        return new AppConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            if (type == AppResponse.class) {
                return new WalletResponseConverter();
            } else if (type == BaseResponse.class) {
                return new BaseResponseConverter();
            } else if (type == FileResponse.class) {
                return new WalletFileConverter();
            }
        } catch (Exception e) {
            LogUtil.e(TAG,e.getMessage());
        }

        return null;

        //return new WalletResponseConverter();
    }

    private class WalletResponseConverter implements Converter<ResponseBody, AppResponse> {
        public WalletResponseConverter(){
        }

        @Override
        public AppResponse convert(ResponseBody value) throws IOException {
            String content = value.string();

            LogUtil.d("httplog", "ResponseBody=\n" + JsonFormatUtil.format(content));
            if (TextUtils.isEmpty(content)) {
                LogUtil.e(TAG, "response is null");
                //服务端返回的数据格式不对
                throw new AppHttpException(AppConstants.SERVER_DATA_FORMAT_ILLEGAL_CODE,
                        AppApplication.getInstance().getString(R.string.error_code_http_exception));
            }

            AppResponse appResponse;
            JSONObject result;
            try {
                appResponse = new AppResponse();
                result = new JSONObject(content);
                appResponse.setResult(result.getString("result"));
                appResponse.setRetinfo(result.getString("retinfo"));
                //result为非0（失败）时，后端是有可能不带data字段的
                //appResponse.setData(result.getString("data"));
            } catch (JSONException e) {
                LogUtil.e(TAG, e.getMessage());
                //服务端返回的数据格式不对
                throw new AppHttpException(AppConstants.SERVER_DATA_FORMAT_ILLEGAL_CODE,
                        AppApplication.getInstance().getString(R.string.error_code_http_exception));
            }

            //0成功，非0失败
            if (!AppResponse.WALLET_HTTP_SUCCESS.equals(appResponse.getResult())) {
//                if (ServerErrorCodes.ERR_BUSINESS_TOKEN_EXPIRE.getErrorCode().equals(appResponse.getResult())
//                        || ServerErrorCodes.ERR_BUSINESS_TOKEN.getErrorCode().equals(appResponse.getResult())) {
//                    //业务token过期异常
//                    throw new AppBizTokenExpiredException(appResponse.getResult(), appResponse.getRetinfo());
//                }else if (ServerErrorCodes.ERR_USER_LOGIN_AT_OTHER_PLACHE.getErrorCode().equals(appResponse.getResult())) {
//                    //踢人事件发出
//                    LoginAtOtherPlaceEvent event = new LoginAtOtherPlaceEvent();
//                    event.alertMessage = appResponse.getRetinfo();
//                    RxBus.getInstance().send(event);
//                    throw new WalletLoginAtOtherPlaceException(appResponse.getResult(), appResponse.getRetinfo());
//                } else if (ServerErrorCodes.LOCK_USER.getErrorCode().equals(appResponse.getResult())) {
//                    //账户被锁定了,强制用户下线
//                    AccountLockedEvent event = new AccountLockedEvent();
//                    event.alertMessage = appResponse.getRetinfo();
//                    RxBus.getInstance().send(event);
//                    throw new WalletAccountLockedException(appResponse.getResult(), appResponse.getRetinfo());
//                } else {
//                    throw new AppHttpException(appResponse.getResult(), appResponse.getRetinfo());
//                }
            } else {
                //将data数据设置放置在这里，增强程序的兼容性
                try {
                    appResponse.setData(result.getString("data"));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    //服务端返回的数据格式不对
                    throw new AppHttpException(AppConstants.SERVER_DATA_FORMAT_ILLEGAL_CODE,
                            AppApplication.getInstance().getString(R.string.error_code_http_exception));
                }
            }

            //返回的数据为空
            if (TextUtils.isEmpty(appResponse.getData())
                    || RESPONSE_DATA_EMPTY.equals(appResponse.getData())) {
                throw new AppEmptyDataException();
            }

            return appResponse;
        }

    }

    private class BaseResponseConverter implements Converter<ResponseBody, BaseResponse> {

        public BaseResponseConverter() {
        }

        @Override
        public BaseResponse convert(ResponseBody value) throws IOException {
            String content = value.string();
            LogUtil.d("httplog", "ResponseBody=\n" + JsonFormatUtil.format(content));
            if (TextUtils.isEmpty(content)) {
                LogUtil.e(TAG, "response is null");
                //服务端返回的数据格式不对
                throw new AppHttpException(AppConstants.SERVER_DATA_FORMAT_ILLEGAL_CODE,
                        AppApplication.getInstance().getString(R.string.error_code_http_exception));
            }

            BaseResponse baseResponse = null;
            try {
                baseResponse = new BaseResponse();
                JSONObject result = new JSONObject(content);
                baseResponse.setResult(result.getString("result"));
                baseResponse.setRetinfo(result.getString("retinfo"));
            } catch (JSONException e) {
                LogUtil.e(TAG, e.getMessage());
                //服务端返回的数据格式不对
                throw new AppHttpException(AppConstants.SERVER_DATA_FORMAT_ILLEGAL_CODE,
                        AppApplication.getInstance().getString(R.string.error_code_http_exception));
            }

            //0成功，非0失败
//            if (!AppResponse.WALLET_HTTP_SUCCESS.equals(baseResponse.getResult())) {
//                if (ServerErrorCodes.ERR_BUSINESS_TOKEN_EXPIRE.getErrorCode().equals(baseResponse.getResult())
//                        || ServerErrorCodes.ERR_BUSINESS_TOKEN.getErrorCode().equals(baseResponse.getResult())) {
//                    //业务token过期异常
//                    throw new AppBizTokenExpiredException(baseResponse.getResult(), baseResponse.getRetinfo());
//                }else if (ServerErrorCodes.ERR_USER_LOGIN_AT_OTHER_PLACHE.getErrorCode().equals(baseResponse.getResult())) {
//                    //踢人事件发出
//                    LoginAtOtherPlaceEvent event = new LoginAtOtherPlaceEvent();
//                    event.alertMessage = baseResponse.getRetinfo();
//                    RxBus.getInstance().send(event);
//                    throw new WalletLoginAtOtherPlaceException(baseResponse.getResult(), baseResponse.getRetinfo());
//                } else if (ServerErrorCodes.LOCK_USER.getErrorCode().equals(baseResponse.getResult())) {
//                    //账户被锁定了,强制用户下线
//                    AccountLockedEvent event = new AccountLockedEvent();
//                    event.alertMessage = baseResponse.getRetinfo();
//                    RxBus.getInstance().send(event);
//                    throw new WalletAccountLockedException(baseResponse.getResult(), baseResponse.getRetinfo());
//                } else {
//                    throw new AppHttpException(baseResponse.getResult(), baseResponse.getRetinfo());
//                }
//            }

            return baseResponse;
        }
    }

    private class WalletFileConverter implements Converter<ResponseBody, FileResponse> {
        public WalletFileConverter() {

        }

        @Override
        public FileResponse convert(ResponseBody value) throws IOException {
            FileResponse response = new FileResponse();
            long contentLength = value.contentLength();
            LogUtil.d("httplog", "FileResponseLength=" + contentLength);
            if (contentLength<=0) {
                return null;
            }
            response.setContentLength(value.contentLength());
            response.setContentType(value.contentType());
            response.setInputStream(value.byteStream());

            return response;
        }
    }



}
