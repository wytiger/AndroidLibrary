package com.wytiger.common.constants;

import java.util.concurrent.TimeUnit;

/**
 * APP相关的全局常量
 */
public class AppConstants {

    /**
     * app本地存储文件根目录
     */
    public static final String APP_ROOT_PATH = "/midea/wallet";

    /**
     * 防止点击抖动时间间隔，单位毫秒
     */
    public static final long ANTI_VIBRATION_DURATION = 1500;
    /**
     * 防止点击抖动时间单位：毫秒
     */
    public static final TimeUnit ANTI_VIBRATION_UNIT = TimeUnit.MILLISECONDS;


    /**
     * 自定义没有网络连接的错误码（16位），
     * 注意不要同后台定义的错误码信息相同
     */
    public static final String NO_NETWORK_CODE = "1111000011110000";


    /**
     * 自定义服务端返回空数据错误码（16位），
     * 注意不要同后台定义的错误码信息相同
     */
    public static final String EMPTY_DATA_CODE = "1111000011110001";
    /**
     * 自定义服务端返回空数据错误信息，表示当前没有网络连接
     */
    public static final String EMPTY_DATA_MESSAGE = "服务端返回的数据为空";


    /**
     * 自定义服务端返回数据格式异常错误吗（16位）,
     * 注意不要同后台定义的错误码信息相同
     */
    public static final String SERVER_DATA_FORMAT_ILLEGAL_CODE = "1111000011110002";
    /**
     * 自定义服务端返回数据格式异常错误信息，表示服务端返回的数据格式不符合规范
     */

    //0：为未实名
    public static final String REAL_NAME_NOT_AUTH = "0";
    //1：为已实名
    public static final String REAL_NAME_AUTH = "1";
    //2：为需完善信息
    public static final String REAL_NAME_AUTH_NOT_COMPLETED = "2";

    //美的宝产品ID
    public static final String MIDEA_BAO_PRODUCT_ID = "10000000";

    //0:默认状态，未设置手势密码 1:手势密码开启2:手势密码关闭
    public static final String N0_GESTURE_PWD = "0";
    public static final String HAS_GESTURE_PWD_AND_ON = "1";
    public static final String HAS_GESTURE_PWD_AND_OFF = "2";

    public static final String HAS_PAY_PWD = "1";
    public static final String NO_PAY_PWD = "0";

    //是否有多个用户可以切换	0:无，1:有
    public static final String NO_MULTI_USER = "0";
    public static final String HAS_MULTI_USER = "1";

    public static final int APP_ONE_PAGE_SIZE = 10;

    //激光推送SharedPreference key值
    public static final String JPUSH_SP_KEY = "jpush_sp_key";
    //新手引导SharedPreference key值
    public static final String GUIDE_SP_KEY = "guide_sp_key";
    public static final String ENVIRONMENT_SP_KEY = "environment_sp_key";

    //public static final String BUIS_TOKEN_EXPIRED_SERVER_CODE = "111071045";

    //数据格式化异常日志TAG
    public static final String DATA_FORMAT_ERROR_LOG_TAG = "data format error in: ";
    //RxBus处理事件异常日志TAG
    public static final String RXBUS_EXCEPTION_LOG_TAG = "RxBus handle event exception in: ";

    public static final int WALLET_PERMISSIONS_REQUEST_CAMERA = 1;


    /***********************************
     * 静态页面地址
     *******************************************/


    /**
     * 地址最大長度
     */
    public static final int MAX_ADDRESS_LENGTH = 25;

    /**
     * 地址最小長度
     */
    public static final int MIN_ADDRESS_LENGTH = 4;


    /**
     * 学校最大長度
     */
    public static final int MAX_SCHOOL_LENGTH = 20;

    /**
     * 学校最小長度
     */
    public static final int MIN_SCHOOL_LENGTH = 4;

    /**
     * 支持最大绑卡数量
     */
    public  static  final int MAX_BOUND_CARD_COUNT = 6;

    /**
     * 绑卡时后台需要识别的场景标识
     */
    public static final String ENTRANCE_BIND_NEW_CARD = "1";
    public static final String ENTRANCE_BIND_NEW_CARD_FOR_PASSWORD= "2";
    public static final String ENTRANCE_BIND_NEW_CARD_FOR_CFCA_CERTIFY = "3";

    public static final String SUBMITTED = "1";

    /***********************************
     * 静态页面地址
     * 如果后台返回，则修改该值
     *******************************************/
    public static  String HEAD_IMGA_START_URL = "https://static.pay1pay.com/img/mideaHome/appWallet/headImg/";

    public static final String FROM_ACTIVITY_CLASS = "from_whitch_activity";

    /**
     * 分隔符
     */
    public static final String SEPARATOR_CHAR = " ";


    /**
     * 身份证号码长度
     */
    public static final int IDENTITY_NUM_LENGTH = 18;
    /**
     * 身份证号码长度+3个空格
     */
    public static final int IDENTITY_NUM_LENGTH_WITHSPACE = IDENTITY_NUM_LENGTH + 2;

    /**
     * 身份证分割样式
     */
    public static final int[] ID_SEPARATOR_PATTERN = new int[]{6, 8, 3, 1};

    /**
     * 银行卡号码分割样式
     */
    public static final int[] CARD_SEPARATOR_PATTERN = new int[]{4, 4, 4, 4, 3};

    /**
     * 手机号码分割样式
     */
    public static final int[] MOBILE_SEPARATOR_PATTERN = new int[]{ 3, 4, 4 };

    /**
     * 长银行卡号码长度
     */
    public static final int BANK_CARD_NUM_LENGTH = 19;
    /**
     * 短银行卡号码长度
     */
    public static final int BANK_CARD_MIN_NUM_LENGTH = 16;

    public static final int LOGIN_PASSWORD_MIN_LENGTH = 8;

    public static final int LOGIN_PASSWROD_MAX_LENGTH = 20;
    /**
     * 验证码长度
     */
    public static final int VERFY_CODE_LENGTH = 6;
    /**
     * 手机号码长度
     */
    public static final int PHONE_NUM_LENGTH = 11;

    /**
     * 手机号码长度
     */
    public static final int PHONE_NUM_LENGTH_WITH2SPACE = PHONE_NUM_LENGTH + 2;


    //设置转账的最大金额
    public static int MAX_TRANSFER_AMOUNT = 10000;

    public static String START_TIMES = "app_start_times_record";

    //上传照片审核状态
    //返回 0:未提交  1：待审核 2：审核通过 3：审核不通过
    public static String IDPHOTO_AUDIT_NONE = "0";
    public static String IDPHOTO_AUDIT_WAITING = "1";
    public static String IDPHOTO_AUDIT_SUCCESS = "2";
    public static String IDPHOTO_AUDIT_FAILE = "3";

    //用户账户等级
    public static final String LEVEL_ONE = "I";
    public static final String LEVEL_TWO = "II";
    public static final String LEVEL_THREE = "III";

    //CFCA后台未统一导致要做区分
    //实名，银行卡认证状态
    //0:未认证，1：已验证
    public static final String CFCA_STATE_BANK_OR_NAME_NONE = "0";
    public static final String CFCA_STATE_BANK_OR_NAME_VERIFYED = "1";
    //手机，学历，驾照认证状态
    //0:未认证，1：认证中，2：已验证，3：失败
    public static final String CFCA_STATE_NONE = "0";
    public static final String CFCA_STATE_VERIFYING = "1";
    public static final String CFCA_STATE_VERIFIED = "2";
    public static final String CFCA_STATE_FAILURE = "3";

    public static final String KEY_NAME = "name";//姓名
    public static final String SELECT_TYPE_EDUCATION = "education";
    public static final String FROM_MOBILE_VERIFY = "MobileVerify";
    public static final String FROM_EDUCATION_VERIFY = "EducationVerify";
    public static final String FROM_DRIVE_VERIFY = "DriveVerify";
    public static final String FROM_FACE_VERIFY = "FaceVerify";

}
