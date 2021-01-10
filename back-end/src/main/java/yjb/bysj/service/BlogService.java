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

    List<Blog> searchAllBlogs();

    // 删除数据库的博客信息
    void deleteBlogByBlogId(Integer blogId);

    List<Blog> searchCheckingBlog();

    // 获取排行榜数据（取前十）
    List<Blog> getChampion();

    // 根据用户id获取该用户的热门文章（按照浏览数）
    List<Blog> getHotBlogsByUserId(Integer userId);

    // 根据用户id获取该用户所有文章的浏览量
    Integer getAllBlogsViewsCountByUserId(Integer userId);
}
