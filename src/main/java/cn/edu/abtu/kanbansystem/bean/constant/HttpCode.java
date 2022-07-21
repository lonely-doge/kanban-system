package cn.edu.abtu.kanbansystem.bean.constant;

/**
 * @author Richard
 * @date 2022/1/29 12:49
 */
public class HttpCode {

    /**
     * 操作成功
     */
    public static final Integer SUCCESS = 200;

    /**
     * 操作失败
     */
    public static final Integer FAILURE = 500;

    /**
     * 没有权限
     */
    public static final Integer NO_PERMISSIONS = 403;

    /**
     * 未登录
     */
    public static final Integer NOT_LOGGED_IN = 401;

    /**
     * 认证失败
     */
    public static final Integer AUTHENTICATION_FAILURE = 405;

    /**
     * token 非法
     */
    public static final Integer ILLEGAL_TOKEN = 501;

    public static final Integer EXPIRED_TOKEN = 502;

    public static final Integer PASSWORD_WRONG = 503;

    public static final Integer IMAGE_NOT_EXIST = 504;

    public static final Integer FILE_UPLOAD_FAILURE = 505;
}
