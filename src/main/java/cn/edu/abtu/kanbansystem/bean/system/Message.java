package cn.edu.abtu.kanbansystem.bean.system;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Richard
 * @date 2022/2/8 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    /**
     * 发送者的id
     */
    private String origin;

    /**
     * 接收者的id
     */
    private String destination;

    /**
     * 需要发送的消息
     */
    private String message;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date time;

}
