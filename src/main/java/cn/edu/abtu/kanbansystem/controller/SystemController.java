package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.dto.CommentDTO;
import cn.edu.abtu.kanbansystem.bean.dto.ProjectInfoDTO;
import cn.edu.abtu.kanbansystem.bean.entity.Project;
import cn.edu.abtu.kanbansystem.bean.entity.Stage;
import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.request.IdRequest;
import cn.edu.abtu.kanbansystem.bean.request.MessageRequest;
import cn.edu.abtu.kanbansystem.bean.request.NameRequest;
import cn.edu.abtu.kanbansystem.bean.request.NoticeRequest;
import cn.edu.abtu.kanbansystem.bean.security.Permission;
import cn.edu.abtu.kanbansystem.bean.security.Role;
import cn.edu.abtu.kanbansystem.bean.security.UserRole;
import cn.edu.abtu.kanbansystem.bean.system.Notice;
import cn.edu.abtu.kanbansystem.bean.vo.NoticeVO;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.mapper.PermissionMapper;
import cn.edu.abtu.kanbansystem.mapper.RoleMapper;
import cn.edu.abtu.kanbansystem.mapper.UserRoleMapper;
import cn.edu.abtu.kanbansystem.service.*;
import cn.edu.abtu.kanbansystem.util.JwtTokenUtil;
import cn.edu.abtu.kanbansystem.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Richard
 * @date 2022/1/29 12:32
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private ISidebarMenuService menuService;

    @Autowired
    private IStageService stageService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping("/sidebarMenu")
    public JsonResult<Object> queryMenu() {
        return new JsonResult<>(HttpCode.SUCCESS, menuService.list(), HttpMsg.SUCCESS_MSG);
    }

    @RequestMapping("/stages")
    public JsonResult<Object> queryStages() {
        return new JsonResult<>(HttpCode.SUCCESS, stageService.list(), HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/addStage")
    @PreAuthorize("hasAuthority('sys:stage:add')")
    public JsonResult<Object> addStage(@RequestBody NameRequest request) {
        boolean isSave = stageService.save(new Stage(request.getName()));
        if (!isSave) throw new BaseException(HttpCode.FAILURE);
        else return new JsonResult<>(HttpCode.SUCCESS);
    }


    @PostMapping("/pullMsg")
    public JsonResult<List<Object>> pullMsg(@RequestBody MessageRequest request) {
        // 根据两人的 id 生成键，并到 redis 中获取
        String key = Stream.of(request.getOrigin(), request.getDestination())
                .sorted()
                .collect(Collectors.joining("-"));
        return new JsonResult<>(HttpCode.SUCCESS,
                redisUtil.pullMessage(key),
                HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryComments")
    public JsonResult<List<CommentDTO>> queryComments(@RequestBody IdRequest request) {
        List<CommentDTO> commentTree = commentService.getAllCommentByBoardId(request.getId());
        return new JsonResult<>(HttpCode.SUCCESS, commentTree, HttpMsg.SUCCESS_MSG);
    }


    @PostMapping("/addComment")
    public JsonResult<Object> addComment(@RequestBody CommentDTO comment) {
        boolean isSave = commentService.save(comment);
        if (isSave)
            return new JsonResult<>(HttpCode.SUCCESS, comment.getId(), HttpMsg.SUCCESS_MSG);
        else
            throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/addNotice")
    public JsonResult<Object> addNotice(@RequestBody NoticeRequest notice) {
        List<Notice> noticeList = notice.getDestination().stream().map(item -> new Notice(notice.getMessage(),
                notice.getOrigin(), item)).collect(Collectors.toList());
        boolean isSave = noticeService.saveBatch(noticeList, noticeList.size());
        if (isSave) return new JsonResult<>(HttpCode.SUCCESS);
        else throw new BaseException(HttpCode.FAILURE);
    }

    @PostMapping("/noticeList")
    public JsonResult<Object> noticeList(@RequestBody IdRequest request) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("origin", request.getId());
        List<Notice> list = noticeService.list(wrapper);
        List<Object> noticeList = list.stream().map(item -> new NoticeVO(item.getId(),
                item.getMessage(),
                userService.getById(item.getOrigin()).getNickName(),
                userService.getById((item.getDestination())).getNickName(),
                item.getCreateTime(),
                item.getUpdateTime())).collect(Collectors.toList());
        return new JsonResult<>(HttpCode.SUCCESS, noticeList, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryNotice")
    public JsonResult<Object> queryNotice() {
        return new JsonResult<>(HttpCode.SUCCESS, noticeService.list(), HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryProjectMembers")
    public JsonResult<Object> queryProjectMembers(@RequestBody IdRequest request) {
        List<User> members = projectService.getById(request.getId()).getMembers();
        return new JsonResult<>(HttpCode.SUCCESS, members, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryUserPermissions")
    public JsonResult<Object> queryUserPermissions(@RequestBody IdRequest request) {
        List<Permission> permissions = permissionMapper.queryPermissionsByUserId(request.getId());
        return new JsonResult<>(HttpCode.SUCCESS, permissions, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryAllPermissions")
    public JsonResult<Object> queryAllPermissions() {
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<>());
        return new JsonResult<>(HttpCode.SUCCESS, permissions, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryUserRoles")
    public JsonResult<Object> queryUserRoles(@RequestBody IdRequest request) {
        List<Role> roles = userRoleMapper.selectRoleByUserId(request.getId());
        return new JsonResult<>(HttpCode.SUCCESS, roles, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/queryAllRoles")
    public JsonResult<Object> queryAllRoles() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<>());
        return new JsonResult<>(HttpCode.SUCCESS, roles, HttpMsg.SUCCESS_MSG);
    }

    @PostMapping("/saveProjectInfo")
    public JsonResult<Object> saveProjectInfo(@RequestBody ProjectInfoDTO projectInfoDTO) {
        // 更新项目信息
        projectService.updateById(new Project(projectInfoDTO.getProjectId(),
                projectInfoDTO.getTitle(),
                projectInfoDTO.getDescribe(),
                projectInfoDTO.getMembers().size() * 2,
                projectInfoDTO.getMembers()));
        // 新的角色列表字符串
        // 删除原本的角色
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", projectInfoDTO.getUserId());
        int delCount = userRoleMapper.delete(userRoleWrapper);
        // 将新的角色添加进去
        if (delCount > 0) {
            QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
            roleWrapper.eq("name", projectInfoDTO.getRoles().get(0));
            List<Role> roles = roleMapper.selectList(roleWrapper);
            for (Role role : roles) {
                userRoleMapper.insert(new UserRole(projectInfoDTO.getUserId(), role.getRoleId()));
            }
        }
        redisUtil.setCacheObject(projectInfoDTO.getUserId(), JwtTokenUtil.createToken(projectInfoDTO.getUserId()));
        return new JsonResult<>(HttpCode.SUCCESS);
    }


}
