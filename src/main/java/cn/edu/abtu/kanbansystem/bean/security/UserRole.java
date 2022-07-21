package cn.edu.abtu.kanbansystem.bean.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Richard
 * @date 2022/5/7 20:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("sys_user_role")
public class UserRole {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @NonNull
    private String userId;

    @NonNull
    private String roleId;
}
