package cn.edu.abtu.kanbansystem.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

/**
 * @author Richard
 * @date 2022/1/18 14:35
 */
@Data
@TableName(value = "team", autoResultMap = true)
public class Team {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String account;

    private String name;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<User> members;
}
