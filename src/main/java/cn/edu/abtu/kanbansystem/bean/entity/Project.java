package cn.edu.abtu.kanbansystem.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Richard
 * @date 2022/1/18 14:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@TableName(value = "project", autoResultMap = true)
public class Project {

    @TableId(type = IdType.ASSIGN_UUID)
    @NonNull
    private String id;

    @NonNull
    private String name;

    private String teamId;

    @NonNull
    private String description;

    @NonNull
    private Integer wip;

    private Boolean isCheck;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @NonNull
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<User> members;
}
