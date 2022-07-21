package cn.edu.abtu.kanbansystem.mapper;

import cn.edu.abtu.kanbansystem.bean.security.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/7 17:05
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户id查询权限
     *
     * @param userId 用户id
     * @return 拥有的权限, 只有权限字符
     */
    List<String> selectPermissionsByUserId(@Param("userId") String userId);


    /**
     * 通过用户id查询权限
     *
     * @param userId 用户id
     * @return 拥有的权限, 权限实体类
     */
    List<Permission> queryPermissionsByUserId(@Param("userId") String userId);

    /**
     * 通过用户id删除角色
     * @param userId
     */
    void deletePermissionsByUserId(@Param("userId") String userId);
}
