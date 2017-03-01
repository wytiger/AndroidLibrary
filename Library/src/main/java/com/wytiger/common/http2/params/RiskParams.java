package com.wytiger.common.http2.params;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: wujiejiang
 * Time: 2016/6/28
 * Desc: 风控参数
 */
public class RiskParams implements Serializable{
    private static final long serialVersionUID = 1L;

    //移动设备ip地址
    private String ip;

    //用户id
    private String userId;

    //移动设备mac地址
    private String mac;

    //移动设备唯一标识
    private String imei;

    private String uuid;

    //移动设备型号
    private String deviceVersion;

    //移动设备处理器信息
    private String cpuInfo;

    //经度
    private String longitude;

    //纬度
    private String latitude;

    //邦胜指纹
    private String fingerPrint;

    //请求时间戳，同基本参数reqTime相同
    private String reqTime;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

//    public static RiskParams newInstance() {
//        RiskParams instance = new RiskParams();
//
//        String charSet = "utf-8";
//
//        try {
//            String ipAddress = DeviceUtils.getIPAddress();
//            if (!TextUtils.isEmpty(ipAddress)) {
//                instance.ip = URLEncoder.encode(ipAddress, charSet);
//            }
//            String macAddress = DeviceUtils.getMacAddress();
//            if (!TextUtils.isEmpty(macAddress)) {
//                instance.mac = URLEncoder.encode(macAddress, charSet);
//            }
//            String tempImei = DeviceUtils.getDeviceId();
//            if (!TextUtils.isEmpty(tempImei)) {
//                instance.imei = URLEncoder.encode(tempImei, charSet);
//            }
//            String tempDeviceVersion = DeviceUtils.getModel();
//            if (!TextUtils.isEmpty(tempDeviceVersion)) {
//                instance.deviceVersion = URLEncoder.encode(tempDeviceVersion, charSet);
//            }
//            String tempCpuInfo = DeviceUtils.getCpuName();
//            if (!TextUtils.isEmpty(tempCpuInfo)) {
//                instance.cpuInfo = URLEncoder.encode(tempCpuInfo, charSet);
//            }
//            String tempUuid = DeviceUtils.getUuid();
//            if (!TextUtils.isEmpty(tempUuid)) {
//                instance.uuid = URLEncoder.encode(tempUuid, charSet);
//            }
//            String tempLongitude = LocationService.getCurrentLocation().getLongitude();
//            if (!TextUtils.isEmpty(tempLongitude)) {
//                instance.longitude = URLEncoder.encode(tempLongitude, charSet);
//            }
//            String tempLatitude = LocationService.getCurrentLocation().getLatitude();
//            if (!TextUtils.isEmpty(tempLatitude)) {
//                instance.latitude = URLEncoder.encode(tempLatitude, charSet);
//            }
//            String tempUserId = UserProfileService.getCurrentUserIdSync();
//            if (!TextUtils.isEmpty(tempUserId)) {
//                instance.userId = URLEncoder.encode(tempUserId,charSet);
//            }
//            String tempReqTime = WalletTimeStampService.getCurrentTimeStamp()+"";
//            if (!TextUtils.isEmpty(tempReqTime)) {
//                instance.reqTime = URLEncoder.encode(tempReqTime, charSet);
//            }
//
//            //同步获取指纹
//            String tempFingerPrint = BSManager.getSyncFingerprint();
//            LogUtils.d("FFF","fingerPrint = " + tempFingerPrint);
//            if (!TextUtils.isEmpty(tempFingerPrint)) {
//                instance.fingerPrint = URLEncoder.encode(tempFingerPrint, charSet);
//            }
//
//        }catch (Exception e) {
//            LogUtils.e("创建风控参数出现异常, e = " + e.getMessage());
//        }
//
//        return instance;
//    }

    public String toEssentialString() {
        List<String> essentialParams = new ArrayList<String>();

        if (!TextUtils.isEmpty(ip)) {
            essentialParams.add("ip=" + ip);
        }
        if (!TextUtils.isEmpty(userId)) {
            essentialParams.add("userId=" + userId);
        }
        if (!TextUtils.isEmpty(mac)) {
            essentialParams.add("mac=" + mac);
        }
        if (!TextUtils.isEmpty(imei)) {
            essentialParams.add("imei=" + imei);
        }
        if (!TextUtils.isEmpty(uuid)) {
            essentialParams.add("uuid=" + uuid);
        }
        if (!TextUtils.isEmpty(deviceVersion)) {
            essentialParams.add("deviceVersion=" + deviceVersion);
        }
        if (!TextUtils.isEmpty(cpuInfo)) {
            essentialParams.add("cpuInfo=" + cpuInfo);
        }
        if (!TextUtils.isEmpty(longitude)) {
            essentialParams.add("longitude=" + longitude);
        }
        if (!TextUtils.isEmpty(latitude)) {
            essentialParams.add("latitude=" + latitude);
        }
        if (!TextUtils.isEmpty(reqTime)) {
            essentialParams.add("reqTime=" + reqTime);
        }
        if (!TextUtils.isEmpty(fingerPrint)) {
            essentialParams.add("fingerPrint=" + fingerPrint);
        }

        //按照字母升序排序
        Collections.sort(essentialParams);

        StringBuilder builder = new StringBuilder();
        for (String param : essentialParams) {
            builder.append(param).append("&");
        }

        //删除最后一个"&"
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
