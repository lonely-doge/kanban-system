package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.security.UserRole;
import cn.edu.abtu.kanbansystem.mapper.UserRoleMapper;
import cn.edu.abtu.kanbansystem.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author RichardDev
 * @date 2022/5/15 21:59
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
