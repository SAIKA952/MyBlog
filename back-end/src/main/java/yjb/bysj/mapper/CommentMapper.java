package yjb.bysj.mapper;

import yjb.bysj.entity.Comment;

import java.util.List;

public interface CommentMapper {
    void submitComment(Comment comment);

    List<Comment> getCommentsByBlogId(Integer blogId);

    Integer getCommentsCountByBlogId(Integer blogId);

    void delectCommentById(Integer commentId);
}
