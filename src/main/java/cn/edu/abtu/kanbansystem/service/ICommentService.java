package cn.edu.abtu.kanbansystem.service;

import cn.edu.abtu.kanbansystem.bean.dto.CommentDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/5 15:59
 */
public interface ICommentService extends IService<CommentDTO> {

    List<CommentDTO> getAllCommentByBoardId(String boardId);
}
