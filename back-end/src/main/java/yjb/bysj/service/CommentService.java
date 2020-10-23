package yjb.bysj.service;

import yjb.bysj.entity.Comment;

import java.util.List;

public interface CommentService {
    void submitComment(Comment comment);

    List<Comment> getCommentsByBlogId(Integer blogId);

    Integer getCommentsCountByBlogId(Integer blogId);

    void deleteCommentById(Integer commentId);
}
