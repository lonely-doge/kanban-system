package cn.edu.abtu.kanbansystem.bean.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Richard
 * @date 2022/5/7 17:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpAddress {

    private String ip;

    private String realAddress;
}
