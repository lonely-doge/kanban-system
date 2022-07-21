package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.entity.Board;
import cn.edu.abtu.kanbansystem.bean.entity.Stage;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.BoardRequest;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.service.IKanbanService;
import cn.edu.abtu.kanbansystem.service.IStageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Richard
 * @date 2022/2/6 10:53
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/kanban")
public class KanbanController {

    @Autowired
    private IKanbanService kanbanService;

    @Autowired
    private IStageService stageService;


    @PostMapping("/queryBoards")
    public JsonResult<Map<String, List<Board>>> queryBoards(@RequestBody BoardRequest request) {
        Map<String, List<Board>> boardsMap = new ConcurrentHashMap<>(16);
        List<Stage> stageList = stageService.list();
        stageList.sort(Comparator.comparing(Stage::getCreateTime));
        QueryWrapper<Board> wrapper = new QueryWrapper<>();
        for (Stage stage : stageList) {
            wrapper.eq("stage", stage.getStage());
            wrapper.eq("project_id", request.getProjectId());
            wrapper.eq("team_id", request.getTeamId());
            wrapper.orderByDesc("update_time");
            List<Board> list = kanbanService.list(wrapper);
            boardsMap.put(stage.getStage(), list);
            wrapper.clear();
        }
        return new JsonResult<>(HttpCode.SUCCESS, boardsMap, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/updateStage")
    public JsonResult<Object> updateStage(@RequestBody Board board) {
        boolean res = kanbanService.updateById(board);
        if (res) return new JsonResult<>(HttpCode.SUCCESS);
        else return new JsonResult<>(HttpCode.FAILURE);
    }

    @PostMapping("/addBoard")
    public JsonResult<Object> addBoard(@RequestBody Board board) {
        boolean isSave = kanbanService.save(board);
        if (isSave) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/updateBoard")
    public JsonResult<Object> updateBoard(@RequestBody Board board) {
        boolean updated = kanbanService.updateById(board);
        if (updated) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE);
    }
}
