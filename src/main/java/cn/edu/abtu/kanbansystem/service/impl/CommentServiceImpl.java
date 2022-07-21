package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.dto.CommentDTO;
import cn.edu.abtu.kanbansystem.mapper.CommentMapper;
import cn.edu.abtu.kanbansystem.service.ICommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Richard
 * @date 2022/5/5 15:59
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDTO> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<CommentDTO> getAllCommentByBoardId(String boardId) {
        QueryWrapper<CommentDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("board_id", boardId);
        wrapper.eq("parent_id", "0");
        List<CommentDTO> commentVos = commentMapper.selectList(wrapper);
        return getCommentTree(commentVos);
    }

    private List<CommentDTO> getCommentTree(String parentId) {
        QueryWrapper<CommentDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.ne("parent_id", "0");
        return commentMapper.selectList(wrapper);
    }


    private List<CommentDTO> getCommentTree(List<CommentDTO> parents) {
        if (parents == null || parents.size() == 0) {
            return null;
        }
        for (CommentDTO parent : parents) {
            String parentId = parent.getId();
            List<CommentDTO> sonCommentVos = getCommentTree(parentId);
            //递归找子评论
            parent.setReplyList(getCommentTree(sonCommentVos));
        }
        return parents;
    }
}
