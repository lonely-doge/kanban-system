package cn.edu.abtu.kanbansystem.bean.dto;

import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/9 22:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDTO {

    private String projectId;

    private String title;

    private String describe;

    private String userId;

    private List<String> roles;

    private List<User> members;
}
