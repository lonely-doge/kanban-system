package cn.edu.abtu.kanbansystem.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Richard
 * @date 2022/2/6 10:36
 */
@Data
@TableName(value = "board", autoResultMap = true)
public class Board {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String kind;

    private String title;

    private String message;

    private String stage;

    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> attachments;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<User> members;

    private String projectId;

    private String teamId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
