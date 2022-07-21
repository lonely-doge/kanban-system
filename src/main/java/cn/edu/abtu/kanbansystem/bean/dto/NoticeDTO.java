package cn.edu.abtu.kanbansystem.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Richard
 * @date 2022/5/9 12:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private String message;

    private String origin;

    private String destination;
}
