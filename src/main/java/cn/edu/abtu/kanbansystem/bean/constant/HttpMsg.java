package cn.edu.abtu.kanbansystem.bean.constant;

/**
 * @author Richard
 * @date 2022/1/29 12:55
 */
public class HttpMsg {

    public static final String SUCCESS_MSG = "操作成功";

    public static final String FAILURE_MSG = "操作失败";

    public static final String NOT_LOGGED_IN = "用户未登录";

    public static final String USER_NOT_PRESENT = "用户名或密码错误";

    public static final String ILLEGAL_TOKEN = "token 非法";

    public static final String EXPIRED_TOKEN = "令牌过期，请重新登录";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String PASSWORD_WRONG = "密码错误";

    public static final String IMAGE_NOT_EXIST = "图片不存在";

    public static final String FILE_UPLOAD_FAILURE = "文件上传失败";

    public static final String AUTHENTICATION_FAILURE = "权限认证失败";
}
