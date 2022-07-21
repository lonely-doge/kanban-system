package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.entity.Project;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.IdRequest;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.service.IProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/4 19:42
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;


    @PostMapping("/queryProjects")
    public JsonResult<List<Project>> queryProjects(@RequestBody IdRequest request) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("team_id", request.getId());
        List<Project> list = projectService.list(wrapper);
        return new JsonResult<>(HttpCode.SUCCESS, list, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/checkProject")
    public JsonResult<Object> checkProject(@RequestBody List<Project> list) {
        boolean res = projectService.updateBatchById(list);
        if (res) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/addProject")
    public JsonResult<Object> addProject(@RequestBody Project project) {
        boolean isSave = projectService.save(project);
        if (isSave) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/queryProjectMember")
    public JsonResult<Object> queryProjectMember(@RequestBody IdRequest request) {
        Project project = projectService.getById(request.getId());
        if (project != null) return new JsonResult<>(HttpCode.SUCCESS, project.getMembers(), HttpMsg.SUCCESS_MSG);
        else throw new BaseException(HttpCode.FAILURE, HttpMsg.FAILURE_MSG);
    }
}
