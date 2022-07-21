package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.entity.Team;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.IdRequest;
import cn.edu.abtu.kanbansystem.bean.request.TeamRequest;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/3 16:33
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @PostMapping("/addTeam")
    public JsonResult<Object> addTeam(@RequestBody TeamRequest request) {
        Team team = new Team();
        BeanUtils.copyProperties(request, team);
        boolean isSave = teamService.save(team);
        if (isSave) return new JsonResult<>(HttpCode.SUCCESS, team.getId(), HttpMsg.SUCCESS_MSG);
        else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/queryTeam")
    public JsonResult<Object> queryTeam(@RequestBody IdRequest request) {
        Team team = teamService.getById(request.getId());
        if (null != team) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE, "不存在该团队");
    }

    @PostMapping("/teamInfoList")
    public JsonResult<Object> teamList() {
        List<Team> teamList = teamService.list();
        return new JsonResult<>(HttpCode.SUCCESS, teamList, HttpMsg.SUCCESS_MSG);
    }
}
