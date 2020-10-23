package yjb.bysj.mapper;

import yjb.bysj.entity.Collect;
import yjb.bysj.entity.Liked;

import java.util.List;

public interface LikedMapper {
    List<Liked> searchAll();

    // 添加点赞信息
    void like(Liked liked);

    // 根据id查找点赞信息
    void searchLikedInfoByLikedId(Integer id);

    // 根据博客id和用户id查找点赞博客的信息
    Liked getBLogLikedInfoById(Liked liked);

    // 根据评论id查找点赞信息
    Liked getCommentLikedInfoById(Liked liked);

    // 更新点赞信息（0变1 1变0）
    void updateLikedStatus(Integer id);

    // 根据博客id查询点赞博客的信息
    List<Liked> getBlogLikedListByBlogId(Integer blogId);

    // 根据用户id和博客id查找该用户是否收藏这篇博客
    Collect getCollectInfoById(Integer userId, Integer blogId);

    // 收藏
    void collect(Collect collect);

    // 更新收藏状态
    void updateCollectStatus(Integer id);

    // 根据博客id查询收藏了这篇博客的信息
    List<Collect> getBlogCollectListByBlogId(Integer blogId);

    // 该用户是否收藏这篇博客
    Collect isUserCollected(Collect collect);

    // 根据用户id查询该用户点赞的文章
    List<Liked> getLikedListByUserId(Integer userId);

    // 根据博客id查找该博客的点赞数
    Integer getLikedCountByBlogId(Integer blogId);

    // 根据博客id查找该博客的收藏数
    Integer getCollectCountByBlogId(Integer blogId);

    // 根据用户id查找该用户收藏的文章
    List<Collect> getCollectListByUserId(Integer userId);

    // 根据用户id获取该用户点赞的文章数
    Integer getLikedCountByUserId(Integer userId);

    // 根据用户id获取该用户收藏的文章数
    Integer getCollectCountByUserId(Integer userId);
}
