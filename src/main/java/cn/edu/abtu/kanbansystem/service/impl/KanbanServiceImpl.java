package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.entity.Board;
import cn.edu.abtu.kanbansystem.mapper.KanbanMapper;
import cn.edu.abtu.kanbansystem.service.IKanbanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/2/22 14:39
 */
@Service
public class KanbanServiceImpl extends ServiceImpl<KanbanMapper, Board> implements IKanbanService {

}
