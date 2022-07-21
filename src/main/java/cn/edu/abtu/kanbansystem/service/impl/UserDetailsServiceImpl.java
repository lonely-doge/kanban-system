package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.security.AuthUser;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.mapper.PermissionMapper;
import cn.edu.abtu.kanbansystem.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Richard
 * @date 2022/2/18 19:51
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", username);
        User user = userMapper.selectOne(wrapper);
        log.info("user = {}", user);
        if (user == null) throw new BaseException(HttpCode.FAILURE, HttpMsg.USER_NOT_PRESENT);
        List<String> permissions = permissionMapper.selectPermissionsByUserId(user.getId());
        AuthorityUtils.commaSeparatedStringToAuthorityList("");
        return new AuthUser(
                user.getId(),
                user.getAccount(),
                user.getPassword(),
                permissions);
    }
}
