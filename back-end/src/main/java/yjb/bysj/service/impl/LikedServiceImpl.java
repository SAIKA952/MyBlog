package yjb.bysj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjb.bysj.entity.Collect;
import yjb.bysj.entity.Liked;
import yjb.bysj.mapper.LikedMapper;
import yjb.bysj.service.LikedService;

import java.util.*;

@Service
public class LikedServiceImpl implements LikedService {

    @Autowired
    private LikedMapper likedMapper;

    @Override
    public List searchAll() {
        List<Liked> list = likedMapper.searchAll();
        return list;
    }

    @Override
    public void like(Liked liked) {
        likedMapper.like(liked);
    }

    @Override
    public Liked searchLikedInfoByLikedId(Integer id) {
        likedMapper.searchLikedInfoByLikedId(id);
        return null;
    }

    @Override
    public Liked getBLogLikedInfoById(Liked liked) {
        Liked blogLiked = likedMapper.getBLogLikedInfoById(liked);
        return blogLiked;
    }

    @Override
    public Liked getCommentLikedInfoById(Liked liked) {
        Liked commentLiked = likedMapper.getCommentLikedInfoById(liked);
        return commentLiked;
    }

    @Override
    public void updateLikedStatus(Integer id) {
        likedMapper.updateLikedStatus(id);
    }

    @Override
    public List<Liked> getBlogLikedListByBlogId(Integer blogId) {
        List<Liked> list = likedMapper.getBlogLikedListByBlogId(blogId);
        return list;
    }

    @Override
    public Collect getCollectInfoById(Integer userId, Integer blogId) {
        Collect collectInfo = likedMapper.getCollectInfoById(userId, blogId);
        return collectInfo;
    }

    @Override
    public void collect(Collect collect) {
        likedMapper.collect(collect);
    }

    @Override
    public void updateCollectStatus(Integer id) {
        likedMapper.updateCollectStatus(id);
    }

    @Override
    public List<Collect> getBlogCollectListByBlogId(Integer blogId) {
        List<Collect> list = likedMapper.getBlogCollectListByBlogId(blogId);
        return list;
    }

    @Override
    public Collect isUserCollected(Collect collect) {
        Collect isCollect = likedMapper.isUserCollected(collect);
        return isCollect;
    }

    @Override
    public List<Liked> getLikedListByUserId(Integer userId) {
        List<Liked> list = likedMapper.getLikedListByUserId(userId);
        return list;
    }

    @Override
    public Integer getLikedCountByBlogId(Integer blogId) {
        Integer likedCount = likedMapper.getLikedCountByBlogId(blogId);
        return likedCount;
    }

    @Override
    public Integer getCollectCountByBlogId(Integer blogId) {
        Integer collectCount = likedMapper.getCollectCountByBlogId(blogId);
        return collectCount;
    }

    @Override
    public List<Collect> getCollectListByUserId(Integer userId) {
        List<Collect> collectList = likedMapper.getCollectListByUserId(userId);
        return collectList;
    }

    // 根据用户id获取该用户点赞的文章数
    @Override
    public Integer getLikedCountByUserId(Integer userId) {
        Integer count = likedMapper.getLikedCountByUserId(userId);
        return count;
    }

    // 根据用户id获取该用户收藏的文章数
    @Override
    public Integer getCollectCountByUserId(Integer userId) {
        Integer count = likedMapper.getCollectCountByUserId(userId);
        return count;
    }
}
