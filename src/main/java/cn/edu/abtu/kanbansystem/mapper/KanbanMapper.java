package cn.edu.abtu.kanbansystem.mapper;

import cn.edu.abtu.kanbansystem.bean.entity.Board;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Richard
 * @date 2022/2/22 14:40
 */
@Repository
public interface KanbanMapper extends BaseMapper<Board> {

//    Boolean addBoard(Board board);
}
