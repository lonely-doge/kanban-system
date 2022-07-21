package cn.edu.abtu.kanbansystem.filter;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.security.AuthUser;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.util.JwtTokenUtil;
import cn.edu.abtu.kanbansystem.util.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Richard
 * @date 2022/1/30 10:35
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String[] uriArr = request.getRequestURI().split("/");
        //获取token
        String token = request.getHeader("Authorization");
        boolean isNull = "".equals(token) || "null".equals(token) || "undefined".equals(token);
        // 如果token为空且请求路径为login, register, addTeam 则直接放行进行登录校验
        if (isNull && ("login".equals(uriArr[3]) ||
                "register".equals(uriArr[3]) ||
                "addTeam".equals(uriArr[3])) ||
                "queryTeam".equals(uriArr[3])
        ) {
            chain.doFilter(request, response);
            return;
        } else if ("OPTIONS".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        } else if ("notice".equals(uriArr[2]) || "chat".equals(uriArr[2])) {
            chain.doFilter(request, response);
            return;
        } else if ("exhibit".equals(uriArr[3])) {
            chain.doFilter(request, response);
            return;
        }
        log.info("request uri = {}, request method = {}, token = {}", request.getRequestURI(), request.getMethod(), token);
        // 否则解析token， 校验token是否合法
        Claims claims = null;
        try {
            claims = JwtTokenUtil.parseToken(token);
            //从redis中获取用户信息
            AuthUser loginUser = (AuthUser) redisUtil.getCacheObject(claims.getSubject());
            //存入SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            log.info("authorities = {}", loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            exceptionResolver.resolveException(request, response, null,
                    new BaseException(HttpCode.ILLEGAL_TOKEN, HttpMsg.ILLEGAL_TOKEN));
            return;
        }
        chain.doFilter(request, response);
    }
}
