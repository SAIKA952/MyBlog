package yjb.bysj.mapper;



import yjb.bysj.entity.Blog;

import java.util.List;

public interface BlogMapper {

    List<Blog> searchAll();

    // 发表博客
    void submitBlog(Blog blog);

    // 根据博客id查询博客信息
    Blog getBlogInfoById(Integer id);

    // 浏览博客时浏览数+1
    void addViews(Integer id);

    // 查找内容
    List<Blog> searchContent(String searchContent);

    // 根据作者id查找作者的所有博客
    List<Blog> getBlogsByAuthorId(Integer id, Integer currentBlogId);

    // 修改博客
    void updateBlog(Blog blog);

    // 根据博客id删除博客
    void deleteBlog(Integer id);

    // 根据博客id查找该博客评论数
    Integer getCommentsByBlogId(Integer blogId);

    // 根据用户id查找该用户的所有博客，按照浏览量降序排序
    List<Blog> getAllBlogsByUserId(Integer userId);

    // 根据用户id查询该用户草稿箱里的博客
    List<Blog> getDraftByUserId(Integer userId);

    // 根据用户id获取该用户写的博客数量
    Integer getBlogCountByUserId(Integer userId);

    // 查找所有博客（后台管理部分）
    List<Blog> searchAllBlogs();

    // 删除数据库里的博客信息
    void deleteBlogByBlogId(Integer blogId);

    // 查找正在审核的文章
    List<Blog> searchCheckingBlog();
}
