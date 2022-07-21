package cn.edu.abtu.kanbansystem.bean.request;

import lombok.Data;

/**
 * @author Richard
 * @date 2022/3/1 20:41
 */
@Data
public class MessageRequest {

    /**
     * 发送者的id
     */
    private String origin;

    /**
     * 接收者的id
     */
    private String destination;
}
