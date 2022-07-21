package cn.edu.abtu.kanbansystem.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Richard
 * @date 2022/2/22 17:18
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@TableName("kanban_stage")
public class Stage implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @NonNull
    private String stage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
