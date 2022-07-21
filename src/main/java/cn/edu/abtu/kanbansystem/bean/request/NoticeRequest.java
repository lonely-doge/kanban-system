package cn.edu.abtu.kanbansystem.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/8 19:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequest {

    private String message;

    private String origin;


    private List<String> destination;
}
