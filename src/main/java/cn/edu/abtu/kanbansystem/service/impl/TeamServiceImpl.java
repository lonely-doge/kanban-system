package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.entity.Team;
import cn.edu.abtu.kanbansystem.mapper.TeamMapper;
import cn.edu.abtu.kanbansystem.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/5/3 16:37
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {
}
