package com.wytiger.common.http2;


import com.wytiger.common.http2.response.AppResponse;
import com.wytiger.common.http2.response.BaseResponse;
import com.wytiger.common.http2.response.FileResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
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

    //获取用户头像
    @POST("/app/account/getHeadPortrait.htm")
    @FormUrlEncoded
    Observable<AppResponse> getHeadPortrait(@Field("busiToken") String busiToken, @Field("headUrl") String headUrl);

    //获取登录密码盐值
    @POST("/app/security/getLoginSalt.htm")
    @FormUrlEncoded
    Observable<AppResponse> getLoginSalt(@Field("loginName") String loginName);

    //多用户需进行身份信息验证
    @POST("/app/auth/toCheckIdentity.htm")
    @FormUrlEncoded
    Observable<AppResponse> multiChannelCheckIdentityForLogin(@Field("payPwd") String payPwd, @Field("pushId") String pushId, @Field("loginName") String loginName, @Field("platId") String platId, @Field("userStatus") String userStatus, @Field("guid") String guid, @Field("operationType") String operationType, @Field("releUserId") String releUserId);

    //用户身份认证时发送短信验证码
    @POST("/app/common/sendSmsForcheckIdentity.htm")
    @FormUrlEncoded
    Observable<BaseResponse> sendSmsForCheckIdentity(@Field("mobile") String mobile);

    //存在多个登录用户名时设置登录密码
    @POST("/app/auth/setLoginPassword.htm")
    @FormUrlEncoded
    Observable<AppResponse> setLoginPassword(@Field("password") String password);

    //免登录
    @POST("/app/auth/singleSignOn.htm")
    @FormUrlEncoded
    Observable<AppResponse> loginAuto(@Field("loginToken") String loginToken);

    //账户切换
    @POST("/app/account/accountChange.htm")
    @FormUrlEncoded
    Observable<AppResponse> accountChange(@Field("busiToken") String busiToken);

    //注册
    @POST("/app/regist/toRegister.htm")
    @FormUrlEncoded
    Observable<AppResponse> register(@Field("mobile") String mobile, @Field("password") String password, @Field("verifyCode") String verifyCode);

    //注册时多用户需进行身份信息验证
    @POST("/app/regist/toCheckIdentity.htm")
    @FormUrlEncoded
    Observable<AppResponse> MultiChannelCheckIdentityForRegister(@Field("payPwd") String payPwd, @Field("pushId") String pushId, @Field("loginName") String loginName, @Field("platId") String platId, @Field("userStatus") String userStatus, @Field("releUserId") String releUserId);

    //注册获取手机验证码
    @POST("/app/regist/getVerifyCodeForRegist.htm")
    @FormUrlEncoded
    Observable<BaseResponse> getVerifyCodeForRegister(@Field("mobile") String mobile);

    //忘记密码获取短信验证码
    @POST("/app/auth/getVerifyCodeForReset.htm")
    @FormUrlEncoded
    Observable<BaseResponse> getVerifyCodeForReset(@Field("mobile") String mobile, @Field("busiToken") String busiToken);


    //手机找回登录密码
    @POST("/app/auth/updatePassword.htm")
    @FormUrlEncoded
    Observable<BaseResponse> modifyLoginPassword(@Field("mobile") String mobile, @Field("password") String password, @Field("verifyCode") String verifyCode);

    //获取时间戳
    @POST("/app/auth/getTimestamp.htm")
    @FormUrlEncoded
    Observable<AppResponse> getTimeStamp(@Field("busiToken") String busiToken);

    //充值-零钱
    @POST("/app/balance/recharge.htm")
    @FormUrlEncoded
    Observable<AppResponse> changeRecharge(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd, @Field("amount") String amount, @Field("bindNo") String bindNo);

    //零钱明细查询
    @POST("/app/trade/chargeList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getBalanceList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //提现下单
    @POST("/app/balance/withdraw.htm")
    @FormUrlEncoded
    Observable<AppResponse> withdrawOrder(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd, @Field("amount") String amount, @Field("bindNo") String bindNo);

    //理财转入下单
    @POST("/app/financeOrder/purchaseOrder.htm")
    @FormUrlEncoded
    Observable<AppResponse> purchaseOrder(@Field("busiToken") String busiToken, @Field("amount") String amount, @Field("fcProductId") String fcProductId, @Field("payType") String payType);

    //理财转入支付
    @POST("/app/finaceTrade/purchasePay.htm")
    @FormUrlEncoded
    Observable<AppResponse> purchasePay(@Field("busiToken") String busiToken, @Field("fcTradeNo") String fcTradeNo, @Field("payPwd") String payPwd, @Field("tradeType") String tradeType, @Field("payType") String payType, @Field("bindNo") String bindNo);

    //查看理财转入结果详情
    @POST("/app/finaceTrade/inResultInfo.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryFundHuaxiaPurchaseResult(@Field("busiToken") String busiToken, @Field("fcTradeNo") String fcTradeNo);

    //理财转出下单
    @POST("/app/financeOrder/redeemOrder.htm")
    @FormUrlEncoded
    Observable<AppResponse> redeemOrder(@Field("busiToken") String busiToken, @Field("amount") String amount, @Field("fcProductId") String fcProductId);

    //理财转出支付
    @POST("/app/finaceTrade/redeemPay.htm")
    @FormUrlEncoded
    Observable<AppResponse> redeemPay(@Field("busiToken") String busiToken, @Field("fcTradeNo") String fcTradeNo, @Field("payPwd") String payPwd, @Field("tradeType") String tradeType, @Field("payType") String payType, @Field("bindNo") String bindNo);

    //获取理财交易记录
    @POST("/app/finance/getUserFinanceTradeList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getUserFinanceTradeList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //获取理财交易记录详情
    @POST("/app/financeOrder/financeTradeDetail.htm")
    @FormUrlEncoded
    Observable<AppResponse> getFinanceTradeDetail(@Field("busiToken") String busiToken, @Field("fcTradeNo") String fcTradeNo);


    //获取用户理财的总资产
    @POST("/app/finance/assetSummary.htm")
    @FormUrlEncoded
    Observable<AppResponse> getassetSummary(@Field("busiToken") String busiToken);

    //人理财卡列表查询
    @POST("/app/financeCard/queryBinding.htm")
    @FormUrlEncoded
    Observable<AppResponse> getFundBankcardList(@Field("busiToken") String busiToken);

    //获取我的基金页面数据
    @POST("/app/finance/fundPage.htm")
    @FormUrlEncoded
    Observable<AppResponse> getMyFundInfo(@Field("busiToken") String busiToken);

    //获取用户购买过的基金列表
    @POST("/app/finance/recentPurchaseList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getRecentlyFundPurchaseList(@Field("busiToken") String busiToken);

    //获取基金产品的详细信息
    @POST("/app/finance/fundDetailInfo.htm")
    @FormUrlEncoded
    Observable<AppResponse> getFundDetailInfo(@Field("busiToken") String busiToken, @Field("fcProductId") String fcProductId);

    //广告列表获取
    @POST("/app/adv/getAdvertisementsList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getAdvertisementsList(@Field("adType") String adType);

    //我的首页
    @POST("/app/account/homePage.htm")
    @FormUrlEncoded
    Observable<AppResponse> getMyInfo(@Field("busiToken") String busiToken);

    //绑卡列表查询
    @POST("/app/card/queryBinding.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryBinding(@Field("busiToken") String busiToken, @Field("businessType") String businessType, @Field("isWithdraw") String isWithdraw);

    //理财产品推荐列表
    @POST("/app/investment/getInvestmentsList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getInvestmentsList(@Field("productType") String productType);

    //交易记录
    @POST("/app/trade/tradeHistory.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryTradeHistory(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize, @Field("tradeType") String tradeType, @Field("tradeStatus") String tradeStatus, @Field("tradeTime") String tradeTime);

    //交易详情查询
    @POST("/app/trade/tradeView.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryTradeDetail(@Field("busiToken") String busiToken, @Field("tradeNo") String tradeNo, @Field("tradeType") String tradeType);

    //查询手机充值记录
    @POST("/app/mobile/rechargeRecord.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryPhoneRechargeHistory(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //查询单次手机充值记录详情
    @POST("/app/mobile/recordDetail.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryPhoneRechargeDetail(@Field("busiToken") String busiToken, @Field("recordNo") String recordNo);


    //用户理财收益列表
    @POST("app/finance/ userFinanceProfitList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getUserFinanceProfitList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //文件上传
    @Multipart
    @POST("/app/file/upload")
    Observable<AppResponse> uploadFileWithParts(@Part() List<MultipartBody.Part> parts);

    @POST("/app/file/upload")
    Observable<AppResponse> uploadFileWithRequestBody(@Body MultipartBody multipartBody);

    //图片加密上传
    @POST("/app/common/uploadImg.htm")
    @FormUrlEncoded
    Observable<AppResponse> uploadImage(@Field("busiToken") String busiToken, @Field("imgFile") String imgFile, @Field("imgSuffix") String imgSuffix, @Field("imgType") String imgType);

    //文件下载
    //https://codeload.github.com/facebook/fresco/zip/master
    @GET
    Observable<FileResponse> downloadFile(@Header("RANGE") long range, @Url String url, @Header(SERVICE_TYPE_KEY) String serviceTYpe);

    /**
     * @param busiToken
     * @return
     */
    @POST("/app/mobile/amountsList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getAmountlist(@Field("busiToken") String busiToken);


    /**
     * 取支付键盘序列与支付密码盐值
     *
     * @param busiToken 业务Token
     * @return
     */
    @POST("/app/common/getInputKeys.htm")
    @FormUrlEncoded
    Observable<AppResponse> getInputKeys(@Field("busiToken") String busiToken);

    /**
     * 用户关联时获取支付键盘序列与支付密码盐值
     *
     * @param releUserId 用户ID
     * @return
     */
    @POST("/app/common/getPayInputKeys.htm")
    @FormUrlEncoded
    Observable<AppResponse> getInputKeysMultiChannel(@Field("releUserId") String releUserId);


    @POST("/app/auth/getTokens.htm")
    @FormUrlEncoded
    Observable<AppResponse> getTokens(@Field("guid") String guid);

    /**
     * 手机充值下单
     *
     * @param busiToken  业务Token
     * @param mobile     充值手机号
     * @param faceAmount 充值面额
     * @param realAmount 支付金额
     * @return
     */
    @POST("/app/mobile/recharge.htm")
    @FormUrlEncoded
    Observable<AppResponse> recharge(@Field("busiToken") String busiToken, @Field("mobile") String mobile, @Field("faceAmount") String faceAmount, @Field("realAmount") String realAmount);

    /**
     * 收银台
     * 获取收银渠道列表，包括支持支付的已绑定的卡、账户余额以及美的宝余额
     *
     * @param busiToken
     * @param tradeType
     * @return
     */
    @POST("/app/trade/cashierChannel.htm")
    @FormUrlEncoded
    Observable<AppResponse> cashierChannel(@Field("busiToken") String busiToken, @Field("tradeType") String tradeType);

    /**
     * 6.2.19	手机归属地及实际支付金额查询
     * 手机归属地及实际支付金额查询
     *
     * @param busiToken
     * @param mobile
     * @return
     */
    @POST("/app/mobile/query.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryMobileAttrs(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    /**
     * 绑定银行卡-校验卡宾
     *
     * @param busiToken
     * @param bankCard
     * @param type
     * @return
     */
    @POST("app/card/cardBin.htm")
    @FormUrlEncoded
    Observable<AppResponse> cardBin(@Field("busiToken") String busiToken
            , @Field("realName") String realName
            , @Field("identityNo") String identityNo
            , @Field("bankCard") String bankCard
            , @Field("businessType") String type
            , @Field("entrance") String entrance);


    /**
     * @param busiToken
     * @param realName
     * @param identityNo
     * @param bankCard
     * @param businessType
     * @param entrance
     * @param cardType
     * @param bindNo
     * @return
     */
    @POST("app/card/cardBin.htm")
    @FormUrlEncoded
    Observable<AppResponse> cardBinForCFCA(
            @Field("busiToken") String busiToken
            , @Field("realName") String realName
            , @Field("identityNo") String identityNo
            , @Field("bankCard") String bankCard
            , @Field("businessType") String businessType
            , @Field("cardType") String cardType
            , @Field("bindNo") String bindNo
            , @Field("entrance") String entrance);


    /**
     * 绑定银行卡-预签约（发送短信）
     *
     * @param busiToken
     * @param mobile
     * @return
     */
    @POST("/app/card/bindBankCardPre.htm")
    @FormUrlEncoded
    Observable<BaseResponse> bindBankCardPre(@Field("busiToken") String busiToken, @Field("mobile") String mobile);


    /**
     * 添加理财卡-预签约（发送短信）
     *
     * @param busiToken
     * @param mobile
     * @return
     */
    @POST("/app/financeCard/bindBankCardPre.htm")
    @FormUrlEncoded
    Observable<BaseResponse> bindFinanceCardPre(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    /**
     * 绑定银行卡-签约
     *
     * @param busiToken
     * @param verifyCode
     * @return
     */
    @POST("/app/card/bindBankCard.htm")
    @FormUrlEncoded
    Observable<AppResponse> bindBankCard(@Field("busiToken") String busiToken, @Field("verifyCode") String verifyCode);

    /**
     * 绑定银行卡-签约
     *
     * @param busiToken
     * @param bindNo
     * @param payPwd
     * @return
     */
    @POST("/app/card/unBind.htm")
    @FormUrlEncoded
    Observable<BaseResponse> unbind(@Field("busiToken") String busiToken, @Field("bindNo") String bindNo, @Field("payPwd") String payPwd);

    /**
     * 设置支付密码
     *
     * @param busiToken
     * @param payPwd
     * @return
     */
    @POST("/app/security/setPayPwd.htm")
    @FormUrlEncoded
    Observable<BaseResponse> setPayPwd(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd);

    /**
     * 验证支付密码
     *
     * @param busiToken
     * @param payPwd
     * @return
     */
    @POST("/app/security/checkPayPwd.htm")
    @FormUrlEncoded
    Observable<BaseResponse> checkPayPwd(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd);

    /**
     * 修改支付密码
     *
     * @param busiToken
     * @param payPwd
     * @return
     */
    @POST("/app/security/updatePayPwd.htm")
    @FormUrlEncoded
    Observable<BaseResponse> updatePayPwd(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd);

    /**
     * 6.2.2	登录进行身份信息验证
     * 单点登录时，如果存在多用户，并且还未与C4A做关联，须先进行身份认证。输入支付密码，进行身份认证
     *
     * @param busiToken
     * @param realName
     * @param identityNo
     * @param cardType
     * @param bankCard
     * @param bindNo
     * @param cvv
     * @return
     */
    @POST("/app/security/toCheckIdentity.htm")
    @FormUrlEncoded
    Observable<AppResponse> toCheckIdentity(@Field("busiToken") String busiToken
            , @Field("realName") String realName
            , @Field("identityNo") String identityNo
            , @Field("cardType") String cardType
            , @Field("bankCard") String bankCard
            , @Field("bindNo") String bindNo
            , @Field("cvv") String cvv);

    /**
     * 已有银行卡作为理财卡
     *
     * @param busiToken
     * @param bindNo
     * @return
     */
    @POST("/app/financeCard/addFinanceCard.htm")
    @FormUrlEncoded
    Observable<AppResponse> addFinanceCard(@Field("busiToken") String busiToken, @Field("bindNo") String bindNo);

    /**
     * 添加理财卡-签约
     *
     * @param busiToken
     * @param verifyCode
     * @return
     */
    @POST("/app/financeCard/bindBankCard.htm")
    @FormUrlEncoded
    Observable<AppResponse> bindFinanceCard(@Field("busiToken") String busiToken, @Field("verifyCode") String verifyCode);

    /**
     * 消息中心数据列表
     *
     * @param busiToken
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("/app/message/messageInfo.htm")
    @FormUrlEncoded
    Observable<AppResponse> getMessageInfoList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    /**
     * 提交反馈信息
     *
     * @param busiToken
     * @param content
     * @param imgUrls   多个图片url，用;隔开
     * @return
     */
    @POST("/app/help/feedback.htm")
    @FormUrlEncoded
    Observable<BaseResponse> feedback(@Field("busiToken") String busiToken
            , @Field("content") String content, @Field("imgUrls") String imgUrls);


    /**
     * @param busiToken
     * @param tradeNo
     * @param password
     * @param payType
     * @param bindNo
     * @return
     */
    @POST("/app/trade/b2c.htm")
    @FormUrlEncoded
    Observable<AppResponse> B2CRecharge(@Field("busiToken") String busiToken
            , @Field("tradeNo") String tradeNo
            , @Field("payPwd") String password
            , @Field("payType") String payType
            , @Field("bindNo") String bindNo);

    /**
     * @param busiToken
     * @return 钱包账户之间的相互转账，包括使用快捷转账或者余额转账
     * //261增加转账时间，该方法废弃"
     */
    @POST("/app/trade/transferToWallet.htm")
    @FormUrlEncoded
    @Deprecated
    Observable<AppResponse> transferToWallet(@Field("busiToken") String busiToken
            , @Field("payPwd") String payPwd
            , @Field("amount") String amount
            , @Field("payType") String payType
            , @Field("bindNo") String bindNo
            , @Field("payeeUserId") String payeeUserId
            , @Field("remark") String remark);

    /**
     * @param busiToken
     * @return 钱包账户之间的相互转账，包括使用快捷转账或者余额转账
     */
    @POST("/app/trade/transferToWallet.htm")
    @FormUrlEncoded
    Observable<AppResponse> transferToWallet(@Field("busiToken") String busiToken
            , @Field("payPwd") String payPwd
            , @Field("amount") String amount
            , @Field("payType") String payType
            , @Field("bindNo") String bindNo
            , @Field("payeeUserId") String payeeUserId
            , @Field("remark") String remark
            , @Field("recMomeyTimeType") String recMoneyTimeType
    );


    /**
     * 转账联系人查询
     * 查询用户最近转账的所有联系人信息列表
     *
     * @param busiToken
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("/app/trade/queryLately.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryLately(@Field("busiToken") String busiToken
            , @Field("pageNo") String pageNo
            , @Field("pageSize") String pageSize);

    /**
     * 转账联系人删除
     * 从最近转账的联系人信息列表中删除指定用户
     *
     * @param busiToken
     * @param bindNo
     * @return
     */
    @POST("/app/trade/deleteContact.htm")
    @FormUrlEncoded
    Observable<BaseResponse> deleteContact(@Field("busiToken") String busiToken
            , @Field("bindNo") String bindNo);

    /**
     * 转账用户查询
     * 根据手机号或者用户名查询转账的账户信息
     *
     * @param busiToken
     * @param userName
     * @return
     */
    @POST("/app/trade/queryTransferPayee.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryTransferPayee(@Field("busiToken") String busiToken
            , @Field("userName") String userName);

    /**
     * 校验用户真实姓名
     * 用户转账时接收方不是转账联系人，需用户输入对方姓氏校验真实姓名
     *
     * @param busiToken
     * @param payeeUserId
     * @param realNamePref
     * @return
     */
    @POST("/app/trade/checkTransferRealName.htm")
    @FormUrlEncoded
    Observable<AppResponse> checkTransferRealName(
            @Field("busiToken") String busiToken
            , @Field("payeeUserId") String payeeUserId
            , @Field("realNamePref") String realNamePref

    );

    /**
     * app退出登录
     *
     * @param busiToken
     * @return
     */
    @POST("/app/auth/loginout.htm")
    @FormUrlEncoded
    Observable<BaseResponse> loginout(@Field("busiToken") String busiToken);


    /**
     * 上传头像
     *
     * @param busiToken
     * @param content
     * @param png
     * @return
     */
    @POST("/app/ account/setHeadPortrait.htm")
    @FormUrlEncoded
    Observable<AppResponse> setHeadPortrait(@Field("busiToken") String busiToken, @Field("imgFile") String content, @Field("imgSuffix") String png);

    /**
     * 查询有效但未绑定为理财卡的卡列表
     *
     * @param busiToken
     * @return
     */
    @POST("/app/financeCard/queryNotBinding.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryNotBinding(@Field("busiToken") String busiToken);

    /**
     * 个人理财卡列表查询
     *
     * @param busiToken
     * @return
     */
    @POST("/app/financeCard/queryBinding.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryFinaceBinding(@Field("busiToken") String busiToken);


    /**
     * 校验理财卡 卡宾
     * 添加理财卡时，根据银行卡号查询此卡类型，以及所属银行信息
     *
     * @param busiToken
     * @param realName
     * @param identityNo
     * @param bankCard
     * @param businessType
     * @return
     */
    @POST("/app/financeCard/cardBin.htm")
    @FormUrlEncoded
    Observable<AppResponse> cardBinFinace(@Field("busiToken") String busiToken
            , @Field("realName") String realName
            , @Field("identityNo") String identityNo
            , @Field("bankCard") String bankCard
            , @Field("businessType") String businessType);

    /**
     * 获取图片
     *
     * @param url
     * @return
     */
    @GET
    Observable<FileResponse> getImage(@Url String url, @Header(SERVICE_TYPE_KEY) String serviceTYpe);

    /**
     * 用户资料查询
     *
     * @param busiToken
     * @return
     */
    @POST("/app/account/detail.htm")
    @FormUrlEncoded
    Observable<AppResponse> getUserDetail(@Field("busiToken") String busiToken);

    /**
     * 用户账户查询
     *
     * @param busiToken
     * @return
     */
    @POST("/app/account/balance.htm")
    @FormUrlEncoded
    Observable<AppResponse> getUserBalance(@Field("busiToken") String busiToken);

    /**
     * 实名认证信息完善
     *
     * @param busiToken
     * @param name
     * @param identiyNo
     * @param experdate
     * @param isLongTerm
     * @param career
     * @param livingArea
     * @param address
     * @return
     */
    @POST("/app/account/improveAuthInfo.htm")
    @FormUrlEncoded
    Observable<BaseResponse> improveAuthInfo(@Field("busiToken") String busiToken
            , @Field("realName") String name
            , @Field("identityNo") String identiyNo
            , @Field("expireDate") String experdate
            , @Field("isLongTerm") String isLongTerm
            , @Field("profession") String career
            , @Field("livingArea") String livingArea
            , @Field("detailAddress") String address);

    /**
     * 查询用户认证情况
     *
     * @param busiToken
     * @return
     */
    @POST("/app/authVerify/queryVerifyAudit.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryVerifyAudit(@Field("busiToken") String busiToken);


    /**
     * 当前账户等级信息查询
     *
     * @param busiToken
     * @return
     */
    @POST("/app/account/gradeDetail.htm")
    @FormUrlEncoded
    Observable<AppResponse> gradeDetail(@Field("busiToken") String busiToken);


    /**
     * 忘记支付密码身份信息验证
     *
     * @param busiToken
     * @param realName
     * @param identityNo
     * @param cardType
     * @param bankCard
     * @param bindNo
     * @return
     */
    @POST("/app/security/toCheckIdentity.htm")
    @FormUrlEncoded
    Observable<BaseResponse> toCheckIdentityForPayword(@Field("busiToken") String busiToken
            , @Field("realName") String realName
            , @Field("identityNo") String identityNo
            , @Field("cardType") String cardType
            , @Field("bankCard") String bankCard
            , @Field("bindNo") String bindNo);

    //忘记支付密码获取短信验证码
    @POST("/app/security/getVerifyCodeForReset.htm")
    @FormUrlEncoded
    Observable<BaseResponse> getVerifyCodeForResetForPayword(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    //忘记支付密码校验短信验证码
    @POST("/app/security/checkVerifyCodeForReset.htm")
    @FormUrlEncoded
    Observable<BaseResponse> checkVerifyCodeForResetForPayword(@Field("busiToken") String busiToken, @Field("verifyCode") String verifyCode);

    /**
     * 查询支持的银行列表
     *
     * @param busiToken
     * @return
     */
    @POST("/app/card/supportBankList.htm")
    @FormUrlEncoded
    Observable<AppResponse> supportBankList(@Field("busiToken") String busiToken);


    /**
     * 邀请对方实名认证
     *
     * @param busiToken
     * @param mobile
     * @return
     */
    @POST("/app/authVerify/inviteRealName.htm")
    @FormUrlEncoded
    Observable<BaseResponse> inviteRealName(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    /*
     * 关联理财账户发送短信
     */
    @POST("/app/financeActRele/sendSms.htm")
    @FormUrlEncoded
    Observable<BaseResponse> sendSmsForFinanceAccountAssociate(@Field("busiToken") String busiToken);

    /*
     * 关联理财账户发送短信
     */
    @POST("/app/financeActRele/verifySms.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifySmsForFinanceAccountAssociate(@Field("busiToken") String busiToken, @Field("verifyCode") String verifyCode);

    /*
     * 关联理财账户时获取支付键盘序列与支付密码盐值
     */
    @POST("/app/financeActRele/getPayInputKeys.htm")
    @FormUrlEncoded
    Observable<AppResponse> getPayInputKeysForFinanceAccountAssociate(@Field("busiToken") String busiToken, @Field("releUserId") String releUserId);


    /*
     * 完成关联理财账户
    */
    @POST("/app/financeActRele/confirm.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyPayPasswordForFinanceAccountAssociate(@Field("busiToken") String busiToken, @Field("payPwd") String payPwd);

    /**
     * 校验用户是否关联理财账户
     *
     * @param busiToken
     * @return
     */
    @POST("/app/financeActRele/verifyResult.htm")
    @FormUrlEncoded
    Observable<AppResponse> associateFinanceAccount(@Field("busiToken") String busiToken);

    //查询历史推送消息列表
    @POST("/app/message/pushMessageList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getPushMessageList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //查询历史反馈列表
    @POST("/app/help/feedbackList.htm")
    @FormUrlEncoded
    Observable<AppResponse> getFeedbackList(@Field("busiToken") String busiToken, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    //权限等级认证
    @POST("/app/authVerify/applyGrade.htm")
    @FormUrlEncoded
    Observable<BaseResponse> applyGrade(@Field("busiToken") String busiToken, @Field("frontImgPath") String frontImgPath, @Field("backImgPath") String backImgPath);


    //查询理财支持的银行列表
    @POST("/app/financeCard/queryBankName.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryBankName(@Field("busiToken") String busiToken);

    //获取app全局参数
    @POST("/app/common/getGlobalParams.htm")
    @FormUrlEncoded
    Observable<AppResponse> getGlobalParams(@Field("busiToken") String busiToken);

    //版本灰度
    @POST("/app/appVersion/updateAppVersion.htm")
    @FormUrlEncoded
    Observable<AppResponse> updateAppVersion(@Field("busiToken") String busiToken);

    //查询是否设置手势密码
    @POST("/app/security/getGesturePwd.htm")
    @FormUrlEncoded
    Observable<AppResponse> checkIfHasGesturePwd(@Field("busiToken") String busiToken);

    //设置手势密码
    @POST("/app/security/setGesturePwd.htm")
    @FormUrlEncoded
    Observable<BaseResponse> setGesturePwd(@Field("busiToken") String busiToken, @Field("gesturePwd") String gesturePwd);

    //关闭打开手势密码
    @POST("/app/security/updateGesturePwdStatus.htm")
    @FormUrlEncoded
    Observable<BaseResponse> updateGesturePwdStatus(@Field("busiToken") String busiToken, @Field("gesturePwdStatus") String gesturePwdStatus);

    //校验手势密码
    @POST("/app/security/checkGesturePwd.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyGesturePwd(@Field("busiToken") String busiToken, @Field("gesturePwd") String gesturePwd);

    //修改手势校验登录密码
    @POST("/app/auth/checkLoginPsw.htm")
    @FormUrlEncoded
    Observable<BaseResponse> checkLoginPwdForModifyGesture(@Field("busiToken") String busiToken, @Field("password") String password, @Field("editCloseFlag") String editCloseFlag);


    //TODO: 后期删除
    //CFCA暂时的测试api
    @POST("/app/test/testVerify.htm")
    @FormUrlEncoded
    Observable<AppResponse> testVerify(@Field("argms") String argms);


    //账户等级认证
    @POST("/app/account/AppAmountLimit.htm")
    @FormUrlEncoded
    Observable<AppResponse> getAllVerifyState(@Field("busiToken") String busiToken);

    //查询支持的银行卡和已绑定旧渠道的银行卡
    @POST("/app/card/queryBindingAndRest.htm")
    @FormUrlEncoded
    Observable<AppResponse> querySupportedAndBindedBankList(@Field("busiToken") String busiToken);

    //为验证银行旧渠道验证发送验证码
    @POST("/app/card/getVerifyCodeForOldChannelBinding.htm")
    @FormUrlEncoded
    Observable<AppResponse> getVerifyCodeForOldChannelBankCard(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    //验证旧渠道银行卡
    @POST("/app/card/oldChannelBindBankAuth.htm")
    @FormUrlEncoded
    Observable<AppResponse> verifyOldChannelBankCard(@Field("busiToken") String busiToken, @Field("bindNo") String bindNo);


    //简化版实名认证
    @POST("/app/identifySimpleVerify/realNameSimpleVerify.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyRealNameSimple(@Field("busiToken") String busiToken, @Field("realName") String realName, @Field("identityNo") String identityNo, @Field("expireDate") String expireDate, @Field("isLongTerm") String isLongTerm);

    //为手机认证发送验证码
    @POST("/app/authVerify/sendVerfyCodeByMobileConfirm.htm")
    @FormUrlEncoded
    Observable<BaseResponse> sendVerfyCodeForMobileVerify(@Field("busiToken") String busiToken, @Field("mobile") String mobile);

    //手机认证
    @POST("/app/authVerify/authAppMobile.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyMobilePhone(@Field("busiToken") String busiToken, @Field("mobile") String mobile, @Field("verifyCode") String verifyCode);


    //学历认证
    @POST("/app/authVerify/appAuthEducation.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyEducation(@Field("busiToken") String busiToken, @Field("highestEducation") String highestEducation, @Field("graduateInstitution") String graduateInstitution, @Field("graduateYear") String graduateYear);

    //驾照验证
    @POST("/app/authVerify/authDrivingLicence.htm")
    @FormUrlEncoded
    Observable<BaseResponse> verifyDrive(@Field("busiToken") String busiToken, @Field("drivingDocumentId") String name, @Field("drivingDocumentId") String id, @Field("drivingDocumentId") String drivingDocumentId);
    //上传人脸识别数据接口
    @POST("/app/authVerify/uploadFaceInfo.htm")
    @FormUrlEncoded
    Observable<AppResponse> uploadFaceInfo(@Field("busiToken") String busiToken, @Field("delta") String delta, @Field("imgBestUrl") String imgBestUrl, @Field("imgEnvUrl") String imgEnvUrl, @Field("imgActionUrls") String imgActionUrls);
    //上传消息推送pushId接口
    @POST("app/common/uploadPushId.htm")
    @FormUrlEncoded
    Observable<BaseResponse> uploadPushId(@Field("busiToken") String busiToken, @Field("pushId") String jPushId);

    //账户无交易及涉案冻结提醒
    @POST("app/accountInfo/withoutTradeFrozenRemind.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryAccountFrozenState(@Field("busiToken") String busiToken);

    //账户冻结激活申请
    @POST("app/accountInfo/submitUnfreeze.htm")
    @FormUrlEncoded
    Observable<BaseResponse> sendActiveAccountRequest(@Field("busiToken") String busiToken, @Field("mobile") String mobile, @Field("email") String email);

    //6.2.129	限额限次提升申请
    @POST("app/accountInfo/increaseLimitSubmit.htm")
    @FormUrlEncoded
    Observable<BaseResponse> increaseLimitSubmit(@Field("busiToken") String busiToken, @Field("increaseReason") String selectedReason);

    //6.2.130	获取到账类型
    @POST("/app/accountInfo/getRecMoneyTimeType.htm")
    @FormUrlEncoded
    Observable<AppResponse> getRecMoneyTimeType(@Field("busiToken") String busiToken);


    //获取是否有3类账号的信息
    @POST("/app/accountInfo/checkAccountLevel.htm")
    @FormUrlEncoded
    Observable<AppResponse> checkAccountLevel(@Field("busiToken") String busiToken);

    //6.2.131	查询是否提交过限额限次
    @POST("/app/accountInfo/queryLimitIncrease.htm")
    @FormUrlEncoded
    Observable<AppResponse> queryLimitIncrease(@Field("busiToken") String busiToken);
}
