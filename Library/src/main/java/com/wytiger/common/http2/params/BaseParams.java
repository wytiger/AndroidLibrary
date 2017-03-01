package com.wytiger.common.http2.params;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wujiejiang
 * Time: 2016/7/4
 * Desc: 基本参数
 */
public class BaseParams implements Serializable{
    private static final long serialVersionUID = 1L;

    //1.接口名称
    private String service;

    //2.接口版本号
    private String version;

    //3.APP版本号
    private String appVersion;

    //4.App客户端类型
    private String appType;

    //5.请求序列号:UUID
    private String reqSeqNumber;

    //6.合作者身份ID
    private String partner;

    //7.通讯级别 1：安全通讯  0：普通通讯
    private String securityLevel;

    //8.参数编码字符集
    private String charset;

    //9.语言
    private String language;

    //10.签名方式
    private String signType;

    //11.请求时间戳，防重放
    private String reqTime;

    private String RSAEncrypt;

    private String MD5withRSA;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getReqSeqNumber() {
        return reqSeqNumber;
    }

    public void setReqSeqNumber(String reqSeqNumber) {
        this.reqSeqNumber = reqSeqNumber;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getRSAEncrypt() {
        return RSAEncrypt;
    }

    public void setRSAEncrypt(String RSAEncrypt) {
        this.RSAEncrypt = RSAEncrypt;
    }

    public String getMD5withRSA() {
        return MD5withRSA;
    }

    public void setMD5withRSA(String MD5withRSA) {
        this.MD5withRSA = MD5withRSA;
    }

    public static BaseParams newInstance() {
        BaseParams instance = new BaseParams();

        instance.appType = "Android";
        instance.charset = "UTF-8";
        instance.signType = "MD5_RSA";
        instance.language = "zh";
        instance.partner = "2000000001";
//        instance.reqSeqNumber = StringUtils.getUUID();
//        //instance.appVersion = AppUtils.getVersionName(AppApplication.getInstance());
//        instance.appVersion = String.valueOf(AppUtils.getVersionCode(AppApplication.getInstance()));
//        instance.reqTime = String.valueOf( WalletTimeStampService.getCurrentTimeStamp());

        return instance;
    }

    /**
     * 如果字段的名称更改，必须修改对应的essentialParams的key值
     *
     * @return
     */
    public List<String> toStringList() {
        List<String> essentialParams = new ArrayList<String>();

        if (!TextUtils.isEmpty(service)) {
            essentialParams.add("service=" + service);
        }
        if (!TextUtils.isEmpty(version)) {
            essentialParams.add("version=" + version);
        }
        if (!TextUtils.isEmpty(appVersion)) {
            essentialParams.add("appVersion=" + appVersion);
        }
        if (!TextUtils.isEmpty(appType)) {
            essentialParams.add("appType=" + appType);
        }
        if (!TextUtils.isEmpty(reqSeqNumber)) {
            essentialParams.add("reqSeqNumber=" + reqSeqNumber);
        }
        if (!TextUtils.isEmpty(partner)) {
            essentialParams.add("partner=" + partner);
        }
        if (!TextUtils.isEmpty(securityLevel)) {
            essentialParams.add("securityLevel=" + securityLevel);
        }
        if (!TextUtils.isEmpty(charset)) {
            essentialParams.add("charset=" + charset);
        }
        if (!TextUtils.isEmpty(language)) {
            essentialParams.add("language=" + language);
        }
        if (!TextUtils.isEmpty(reqTime)) {
            essentialParams.add("reqTime=" + reqTime);
        }
        if (!TextUtils.isEmpty(signType)) {
            essentialParams.add("signType=" + signType);
        }

        return essentialParams;
    }
}
