package cn.edu.abtu.kanbansystem.util;

import cn.edu.abtu.kanbansystem.bean.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Richard
 * @date 2022/2/17 19:03
 */
public class SecurityUtil {
    /**
     * 获取当前用户信息
     */
    public static AuthUser getUserInfo() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前用户ID
     */
    public static String getUserId() {
        return getUserInfo().getId();
    }

    /**
     * 获取当前用户账号
     */
    public static String getUserName() {
        return getUserInfo().getUsername();
    }
}
