package cn.edu.abtu.kanbansystem.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/8 8:50
 */
@Data
public class RolePermissionDTO {

    private List<String> roleName;

    private List<String> flag;
}
