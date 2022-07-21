package cn.edu.abtu.kanbansystem;

import cn.edu.abtu.kanbansystem.bean.security.Permission;
import cn.edu.abtu.kanbansystem.bean.security.Role;
import cn.edu.abtu.kanbansystem.bean.security.RolePermission;
import cn.edu.abtu.kanbansystem.bean.security.UserRole;
import cn.edu.abtu.kanbansystem.mapper.PermissionMapper;
import cn.edu.abtu.kanbansystem.mapper.RoleMapper;
import cn.edu.abtu.kanbansystem.mapper.RolePermissionMapper;
import cn.edu.abtu.kanbansystem.mapper.UserRoleMapper;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KanbanSystemApplicationTests {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    void insertPermission() {
        permissionMapper.insert(new Permission("添加阶段", "sys:stage:add"));
    }

    @Test
    void testQueryPermissions() {
        List<String> permissions = permissionMapper.selectPermissionsByUserId("98cda36902e3b3ee2766f00752ddb0b4");
        for (String permission : permissions) {
            System.out.println("permission = " + permission);
        }
    }

    @Test
    void insertRole() {
        roleMapper.insert(new Role("WA", "网站管理员"));
        roleMapper.insert(new Role("PM", "项目经理"));
        roleMapper.insert(new Role("Dev", "开发者"));
    }

    @Test
    void testIpUtil() {
        String ipStr = "171.91.14.79";
//        String ip = "0:0:0:0:0:0:0:1".equals(ipStr) ? "127.0.0.1" : ipStr;
        String res = HttpUtil.get("https://www.ip138.com/iplookup.asp?ip=" + ipStr + "&action=2");
        String realAddress = res.split("\"ASN归属地\":\"")[1].split("\", \"iP段\":")[0];
        System.out.println("realAddress = " + realAddress);
    }


    @Test
    void testInsertRolePermission() {
        rolePermissionMapper.insert(new RolePermission("e6bec36c34352c103ab7588aee331939", "5c8c0f2ef2d4153e4f9120dae30439f2"));
    }

    @Test
    void testInsertUserRole() {
        userRoleMapper.insert(new UserRole("a3d791c307fae65afb755418578a0354", "34b74bbffa5bdb0540885c9d1bcfaf89"));
    }

}
