package cn.edu.abtu.kanbansystem.mapper;

import cn.edu.abtu.kanbansystem.bean.security.Role;
import cn.edu.abtu.kanbansystem.bean.security.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/7 20:42
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<Role> selectRoleByUserId(@Param("userId") String userId);
}
