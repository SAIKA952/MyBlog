package yjb.bysj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Blog;
import yjb.bysj.mapper.BlogMapper;
import yjb.bysj.service.BlogService;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> searchAll() {
        List<Blog> list = blogMapper.searchAll();
        return list;
    }

    @Override
    public Res submitBlog(Blog blog) {
        blogMapper.submitBlog(blog);
        return Res.ok();
    }

    @Override
    public Blog getBlogInfoById(Integer id) {
        Blog blog = blogMapper.getBlogInfoById(id);
        return blog;
    }

    @Override
    public void addViews(Integer id) {
        blogMapper.addViews(id);
    }

    @Override
    public List<Blog> searchContent(String searchContent) {
        List<Blog> list = blogMapper.searchContent(searchContent);
        return list;
    }

    @Override
    public List<Blog> getBlogsByAuthorId(Integer id, Integer currentBlogId) {
        List<Blog> list =  blogMapper.getBlogsByAuthorId(id, currentBlogId);
        return list;
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    @Override
    public void deleteBlog(Integer id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public Integer getCommentsByBlogId(Integer blogId) {
        Integer count = blogMapper.getCommentsByBlogId(blogId);
        return count;
    }

    @Override
    public List<Blog> getHotBlogsByUserId(Integer userId) {
        List<Blog> list = blogMapper.getHotBlogsByUserId(userId);
        return list;
    }

    @Override
    public List<Blog> getAllBlogsByUserId(Integer userId) {
        List<Blog> list = blogMapper.getAllBlogsByUserId(userId);
        return list;
    }

    @Override
    public List<Blog> getDraftByUserId(Integer userId) {
        List<Blog> list = blogMapper.getDraftByUserId(userId);
        return list;
    }


    // 根据用户id获取该用户写的博客数量
    @Override
    public Integer getBlogCountByUserId(Integer userId) {
        Integer count = blogMapper.getBlogCountByUserId(userId);
        return count;
    }

    @Override
    public List<Blog> searchAllBlogs() {
        List<Blog> list = blogMapper.searchAllBlogs();
        return list;
    }

    @Override
    public void deleteBlogByBlogId(Integer blogId) {
        blogMapper.deleteBlogByBlogId(blogId);
    }

    @Override
    public List<Blog> searchCheckingBlog() {
        List<Blog> list = blogMapper.searchCheckingBlog();
        return list;
    }

    @Override
    public List<Blog> getChampion() {
        List<Blog> blogs = blogMapper.getChampion();
        return blogs;
    }
}
