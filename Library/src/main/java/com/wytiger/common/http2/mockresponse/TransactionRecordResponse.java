package com.wytiger.common.http2.mockresponse;


import com.wytiger.common.http2.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class TransactionRecordResponse extends BaseResponse {
    List<TransactionRecordMocker> data;

    //获取第一页数据
    public static TransactionRecordResponse getPageOne() {
        TransactionRecordResponse response = new TransactionRecordResponse();
        response.setResult("0");
        response.setRetinfo("获取交易记录");

        List<TransactionRecordMocker> datalist = new ArrayList<>();
        TransactionRecordMocker mocker1 = new TransactionRecordMocker();
        mocker1.dayOfWeek = "周日";
        mocker1.target = "联通";
        mocker1.targetDesc = "中国联通";
        mocker1.date = "04-29";
        mocker1.amount = "1000";
        datalist.add(mocker1);

        TransactionRecordMocker mocker2 = new TransactionRecordMocker();
        mocker2.dayOfWeek = "周日";
        mocker2.target = "联通";
        mocker2.targetDesc = "中国联通";
        mocker2.date = "04-28";
        mocker2.amount = "1000";
        datalist.add(mocker2);

        TransactionRecordMocker mocker3 = new TransactionRecordMocker();
        mocker1.dayOfWeek = "周日";
        mocker3.target = "联通";
        mocker3.targetDesc = "中国联通";
        mocker3.date = "04-27";
        mocker3.amount = "1000";
        datalist.add(mocker3);

        TransactionRecordMocker mocker4 = new TransactionRecordMocker();
        mocker4.dayOfWeek = "周日";
        mocker4.target = "联通";
        mocker4.targetDesc = "中国联通";
        mocker4.date = "04-26";
        mocker4.amount = "1000";
        datalist.add(mocker4);

        TransactionRecordMocker mocker5 = new TransactionRecordMocker();
        mocker5.dayOfWeek = "周日";
        mocker5.target = "联通";
        mocker5.targetDesc = "中国联通";
        mocker5.date = "04-25";
        mocker5.amount = "1000";
        datalist.add(mocker5);

        TransactionRecordMocker mocker6 = new TransactionRecordMocker();
        mocker6.dayOfWeek = "周日";
        mocker6.target = "联通";
        mocker6.targetDesc = "中国联通";
        mocker6.date = "04-24";
        mocker6.amount = "1000";
        datalist.add(mocker6);

        TransactionRecordMocker mocker7 = new TransactionRecordMocker();
        mocker7.dayOfWeek = "周日";
        mocker7.target = "联通";
        mocker7.targetDesc = "中国联通";
        mocker7.date = "04-23";
        mocker7.amount = "1000";
        datalist.add(mocker7);

        TransactionRecordMocker mocker8 = new TransactionRecordMocker();
        mocker8.dayOfWeek = "周日";
        mocker8.target = "联通";
        mocker8.targetDesc = "中国联通";
        mocker8.date = "04-22";
        mocker8.amount = "1000";
        datalist.add(mocker8);

        TransactionRecordMocker mocker9 = new TransactionRecordMocker();
        mocker9.dayOfWeek = "周日";
        mocker9.target = "联通";
        mocker9.targetDesc = "中国联通";
        mocker9.date = "03-16";
        mocker9.amount = "1000";
        datalist.add(mocker9);

        TransactionRecordMocker mocker10 = new TransactionRecordMocker();
        mocker10.dayOfWeek = "周日";
        mocker10.target = "联通";
        mocker10.targetDesc = "中国联通";
        mocker10.date = "03-15";
        mocker10.amount = "1000";
        datalist.add(mocker10);

        response.data = datalist;

        return response;
    }

    //获取第二页数据
    public static TransactionRecordResponse getPageTwo() {
        TransactionRecordResponse response = new TransactionRecordResponse();
        response.setResult("0");
        response.setRetinfo("获取交易记录");

        List<TransactionRecordMocker> datalist = new ArrayList<>();
        TransactionRecordMocker mocker1 = new TransactionRecordMocker();
        mocker1.dayOfWeek = "周日";
        mocker1.target = "联通";
        mocker1.targetDesc = "中国联通";
        mocker1.date = "03-13";
        mocker1.amount = "1000";
        datalist.add(mocker1);

        TransactionRecordMocker mocker2 = new TransactionRecordMocker();
        mocker2.dayOfWeek = "周日";
        mocker2.target = "联通";
        mocker2.targetDesc = "中国联通";
        mocker2.date = "03-12";
        mocker2.amount = "1000";
        datalist.add(mocker2);

        TransactionRecordMocker mocker3 = new TransactionRecordMocker();
        mocker1.dayOfWeek = "周日";
        mocker3.target = "联通";
        mocker3.targetDesc = "中国联通";
        mocker3.date = "03-10";
        mocker3.amount = "1000";
        datalist.add(mocker3);

        TransactionRecordMocker mocker4 = new TransactionRecordMocker();
        mocker4.dayOfWeek = "周日";
        mocker4.target = "联通";
        mocker4.targetDesc = "中国联通";
        mocker4.date = "02-26";
        mocker4.amount = "1000";
        datalist.add(mocker4);

        TransactionRecordMocker mocker5 = new TransactionRecordMocker();
        mocker5.dayOfWeek = "周日";
        mocker5.target = "联通";
        mocker5.targetDesc = "中国联通";
        mocker5.date = "02-25";
        mocker5.amount = "1000";
        datalist.add(mocker5);

        TransactionRecordMocker mocker6 = new TransactionRecordMocker();
        mocker6.dayOfWeek = "周日";
        mocker6.target = "联通";
        mocker6.targetDesc = "中国联通";
        mocker6.date = "02-24";
        mocker6.amount = "1000";
        datalist.add(mocker6);

        TransactionRecordMocker mocker7 = new TransactionRecordMocker();
        mocker7.dayOfWeek = "周日";
        mocker7.target = "联通";
        mocker7.targetDesc = "中国联通";
        mocker7.date = "02-23";
        mocker7.amount = "1000";
        datalist.add(mocker7);

        TransactionRecordMocker mocker8 = new TransactionRecordMocker();
        mocker8.dayOfWeek = "周日";
        mocker8.target = "联通";
        mocker8.targetDesc = "中国联通";
        mocker8.date = "02-22";
        mocker8.amount = "1000";
        datalist.add(mocker8);

        TransactionRecordMocker mocker9 = new TransactionRecordMocker();
        mocker9.dayOfWeek = "周日";
        mocker9.target = "联通";
        mocker9.targetDesc = "中国联通";
        mocker9.date = "02-16";
        mocker9.amount = "1000";
        datalist.add(mocker9);

        TransactionRecordMocker mocker10 = new TransactionRecordMocker();
        mocker10.dayOfWeek = "周日";
        mocker10.target = "联通";
        mocker10.targetDesc = "中国联通";
        mocker10.date = "02-15";
        mocker10.amount = "1000";
        datalist.add(mocker10);

        response.data = datalist;

        return response;
    }

    static class TransactionRecordMocker {
        String dayOfWeek;
        String target;
        String targetDesc;
        String date;
        String amount;
    }
}
