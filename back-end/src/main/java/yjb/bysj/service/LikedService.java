package yjb.bysj.service;

import yjb.bysj.entity.Collect;
import yjb.bysj.entity.Liked;

import java.util.*;

public interface LikedService {
    List<Liked> searchAll();

    void like(Liked liked);

    Liked searchLikedInfoByLikedId(Integer id);

    Liked getBLogLikedInfoById(Liked liked);

    Liked getCommentLikedInfoById(Liked liked);

    void updateLikedStatus(Integer id);

    List<Liked> getBlogLikedListByBlogId(Integer blogId);

    Collect getCollectInfoById(Integer userId, Integer blogId);

    void collect(Collect collect);

    void updateCollectStatus(Integer id);

    List<Collect> getBlogCollectListByBlogId(Integer blogId);

    Collect isUserCollected(Collect collect);

    List<Liked> getLikedListByUserId(Integer userId);

    Integer getLikedCountByBlogId(Integer blogId);

    Integer getCollectCountByBlogId(Integer blogId);

    List<Collect> getCollectListByUserId(Integer userId);

    Integer getLikedCountByUserId(Integer userId);

    Integer getCollectCountByUserId(Integer userId);

    Integer getAllBlogsCollectCountByUserId(Integer userId);

    Integer getAllBlogsLikedCountByUserId(Integer userId);
}
