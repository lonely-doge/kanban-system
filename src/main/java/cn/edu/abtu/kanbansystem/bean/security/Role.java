package cn.edu.abtu.kanbansystem.bean.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Richard
 * @date 2022/5/7 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@TableName("sys_role")
public class Role {

    @TableId(type = IdType.ASSIGN_UUID)
    private String roleId;

    @NonNull
    private String flag;

    @NonNull
    private String name;
}
