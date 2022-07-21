package cn.edu.abtu.kanbansystem.bean.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Richard
 * @date 2022/1/29 12:26
 */
@Data
@TableName("menu")
public class SidebarMenu {

    private String id;

    private String name;
}
