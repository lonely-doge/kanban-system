package cn.edu.abtu.kanbansystem.service;

import cn.edu.abtu.kanbansystem.bean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Richard
 * @date 2022/1/18
 */
public interface IUserService extends IService<User> {

    /**
     * 查询团队成员
     * @param teamId 团队id
     * @param userId 用户id
     * @return 成员列表
     */
    List<User> queryTeamMember(String teamId, String userId);

    Boolean addUser(User user);
}
