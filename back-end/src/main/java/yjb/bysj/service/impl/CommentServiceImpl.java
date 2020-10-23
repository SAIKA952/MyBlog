package yjb.bysj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjb.bysj.entity.Comment;
import yjb.bysj.mapper.BlogMapper;
import yjb.bysj.mapper.CommentMapper;
import yjb.bysj.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void submitComment(Comment comment) {
        commentMapper.submitComment(comment);
    }

    @Override
    public List<Comment> getCommentsByBlogId(Integer blogId) {
        List<Comment> list = commentMapper.getCommentsByBlogId(blogId);
        return list;
    }

    @Override
    public Integer getCommentsCountByBlogId(Integer blogId) {
        Integer count = commentMapper.getCommentsCountByBlogId(blogId);
        return count;
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        commentMapper.delectCommentById(commentId);
    }


}
