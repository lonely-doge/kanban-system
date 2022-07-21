package cn.edu.abtu.kanbansystem.bean.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Richard
 * @date 2022/5/7 20:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("sys_role_permissions")
public class RolePermission {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @NonNull
    private String roleId;

    @NonNull
    private String permissionId;
}
