package cn.edu.abtu.kanbansystem.bean.dto;

import cn.edu.abtu.kanbansystem.bean.entity.User;
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
import java.util.TimeZone;

/**
 * @author Richard
 * @date 2022/5/5 15:48
 */
@Data
@TableName(value = "board_comments", autoResultMap = true)
public class CommentDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private User userInfo;

    private String parentId;

    private String boardId;

    private String content;

    @TableField(exist = false)
    private List<CommentDTO> replyList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
