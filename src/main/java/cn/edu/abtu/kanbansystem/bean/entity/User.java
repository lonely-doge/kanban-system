package cn.edu.abtu.kanbansystem.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Richard
 * @date 2022/1/18
 */
@Data
@TableName("sys_user")
public class User {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String account;

    private String password;

    private String nickName;

    private String signature;

    private String teamId;

    private String status;

    private String gender;

    private String avatar;

    private Integer delFlag;
}
