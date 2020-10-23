package yjb.bysj.service;


import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> searchAll();

    Res submitBlog(Blog blog);

    Blog getBlogInfoById(Integer id);

    void addViews(Integer id);

    List<Blog> searchContent(String searchContent);

    List<Blog> getBlogsByAuthorId(Integer id, Integer currentBlogId);

    void updateBlog(Blog blog);

    void deleteBlog(Integer id);

    Integer getCommentsByBlogId(Integer blogId);

    List<Blog> getAllBlogsByUserId(Integer userId);

    List<Blog> getDraftByUserId(Integer userId);

    Integer getBlogCountByUserId(Integer userId);
}
