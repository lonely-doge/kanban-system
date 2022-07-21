package cn.edu.abtu.kanbansystem.bean.system;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.*;

import java.util.Date;

/**
 * @author Richard
 * @date 2022/5/8 19:22
 */
@Data
@TableName("notice")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Notice {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @NonNull
    private String message;

    @NonNull
    private String origin;

    @NonNull
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
