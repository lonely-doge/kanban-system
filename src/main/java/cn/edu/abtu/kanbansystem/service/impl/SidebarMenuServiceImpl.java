package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.system.SidebarMenu;
import cn.edu.abtu.kanbansystem.mapper.SidebarMenuMapper;
import cn.edu.abtu.kanbansystem.service.ISidebarMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/1/29 13:05
 */
@Service
public class SidebarMenuServiceImpl extends ServiceImpl<SidebarMenuMapper, SidebarMenu> implements ISidebarMenuService {
}
