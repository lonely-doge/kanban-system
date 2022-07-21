package cn.edu.abtu.kanbansystem.bean.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Richard
 * @date 2022/5/7 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("sys_permissions")
public class Permission {

    @TableId(type = IdType.ASSIGN_UUID)
    private String permissionId;

    @NonNull
    private String name;

    @NonNull
    private String flag;


}
