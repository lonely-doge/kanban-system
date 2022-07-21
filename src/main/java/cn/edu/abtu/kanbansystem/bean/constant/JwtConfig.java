package cn.edu.abtu.kanbansystem.bean.constant;

import lombok.Data;

/**
 * @author Richard
 * @date 2022/1/18
 */
@Data
public class JwtConfig {

    /**
     * 密钥KEY
     */
    public static final String SECRET = "zhb-Admin-secret";
    /**
     * TokenHeader
     */
    public static final String TOKEN_HEADER = "Bearer";
    /**
     * Token前缀字符
     */
    public static final String TOKEN_PREFIX = "kanban";
    /**
     * 1000 * 60 * 60 * 24 一天 (单位ms)
     * 过期时间
     */
    public static final Long EXPIRATION = 86400000L;
}
