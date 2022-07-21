package cn.edu.abtu.kanbansystem.service;

import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.UserRequest;

/**
 * @author Richard
 * @date 2022/2/18 20:18
 */
public interface LoginService {

    /**
     * 登录
     */
    JsonResult<Object> login(UserRequest user);

    /**
     * 登出
     */
    JsonResult<Object> logout();
}
