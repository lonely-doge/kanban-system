package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.system.Notice;
import cn.edu.abtu.kanbansystem.mapper.NoticeMapper;
import cn.edu.abtu.kanbansystem.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/5/8 19:41
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
}
