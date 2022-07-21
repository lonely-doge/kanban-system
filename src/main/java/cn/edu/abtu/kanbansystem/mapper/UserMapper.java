package cn.edu.abtu.kanbansystem.mapper;

import cn.edu.abtu.kanbansystem.bean.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Richard
 * @date 2022/1/18
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> queryTeamMember(@Param("teamId") String teamId,
                               @Param("userId") String userId);
}
