package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.entity.Project;
import cn.edu.abtu.kanbansystem.mapper.ProjectMapper;
import cn.edu.abtu.kanbansystem.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/4/27 9:40
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
