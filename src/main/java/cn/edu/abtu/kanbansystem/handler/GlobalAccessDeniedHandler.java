package cn.edu.abtu.kanbansystem.handler;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理
 *
 * @author Richard
 * @date 2022/2/21 22:51
 */
@Slf4j
@Component
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("access denied = {}", accessDeniedException.getMessage());
        ResponseUtil.renderString(response, HttpCode.AUTHENTICATION_FAILURE, HttpMsg.AUTHENTICATION_FAILURE);
    }
}
