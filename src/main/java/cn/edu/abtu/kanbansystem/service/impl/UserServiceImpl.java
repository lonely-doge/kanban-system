package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.mapper.UserMapper;
import cn.edu.abtu.kanbansystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Richard
 * @date 2022/1/18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryTeamMember(String teamId, String userId) {
        return userMapper.queryTeamMember(teamId, userId);
    }

    @Override
    public Boolean addUser(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPwd = encoder.encode(user.getPassword());
        user.setPassword(secretPwd);
        return userMapper.insert(user) > 0;
    }
}
