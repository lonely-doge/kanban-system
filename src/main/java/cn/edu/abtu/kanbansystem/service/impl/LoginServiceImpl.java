package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.UserRequest;
import cn.edu.abtu.kanbansystem.bean.security.AuthUser;
import cn.edu.abtu.kanbansystem.bean.security.Role;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.mapper.UserMapper;
import cn.edu.abtu.kanbansystem.mapper.UserRoleMapper;
import cn.edu.abtu.kanbansystem.service.LoginService;
import cn.edu.abtu.kanbansystem.util.JwtTokenUtil;
import cn.edu.abtu.kanbansystem.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Richard
 * @date 2022/2/18 20:20
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public JsonResult<Object> login(UserRequest user) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            AuthUser principal = (AuthUser) authenticate.getPrincipal();
            String token = JwtTokenUtil.createToken(principal.getId());
            redisUtil.setCacheObject(principal.getId(), principal);
            List<String> roles = userRoleMapper.selectRoleByUserId(principal.getId()).stream().map(Role::getFlag).collect(Collectors.toList());
            JSONObject result = new JSONObject();
            result.put("token", token);
            result.put("role", roles.get(0));
            result.put("userInfo", userMapper.selectById(principal.getId()));
            return new JsonResult<>(HttpCode.SUCCESS, result, HttpMsg.LOGIN_SUCCESS);
        } catch (Exception e) {
            throw new BaseException(HttpCode.FAILURE, e.getMessage());
        }
    }

    @Override
    public JsonResult<Object> logout() {
        return null;
    }
}
