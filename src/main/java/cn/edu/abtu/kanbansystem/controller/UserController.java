package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.dto.RegisterDTO;
import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.TeamMemberRequest;
import cn.edu.abtu.kanbansystem.bean.request.UserRequest;
import cn.edu.abtu.kanbansystem.bean.security.UserRole;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.service.IUserRoleService;
import cn.edu.abtu.kanbansystem.service.IUserService;
import cn.edu.abtu.kanbansystem.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Richard
 * @date 2022/1/18
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private IUserRoleService userRoleService;

    @PostMapping(value = "/login")
    public JsonResult<Object> login(@RequestBody UserRequest request) {
        return loginService.login(request);
    }

    @PostMapping("/logout")
    public JsonResult<Object> logout() {
        return new JsonResult<>(HttpCode.SUCCESS);
    }

    @PostMapping("/register")
    public JsonResult<User> register(@RequestBody RegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        user.setDelFlag(0);
        user.setStatus("NORMAL");
        Boolean isSave = userService.addUser(user);
        if (isSave) {
            if ("create".equals(registerDTO.getType()))
                userRoleService.save(new UserRole(user.getId(), "e6bec36c34352c103ab7588aee331939"));
            else if ("join".equals(registerDTO.getType()))
                userRoleService.save(new UserRole(user.getId(), "34b74bbffa5bdb0540885c9d1bcfaf89"));
            return new JsonResult<>(HttpCode.SUCCESS, user, HttpMsg.SUCCESS_MSG);
        } else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/queryTeamMember")
    public JsonResult<Object> queryTeamMember(@RequestBody TeamMemberRequest request) {
        return new JsonResult<>(HttpCode.SUCCESS,
                userService.queryTeamMember(request.getTeamId(), request.getUserId()),
                HttpMsg.SUCCESS_MSG);
    }

}
