package yjb.bysj.mapper;

import yjb.bysj.entity.Account;
import yjb.bysj.entity.Follow;

import java.util.List;

public interface FollowMapper {

    // 查找所有
    List<Follow> searchAll();

    // 关注用户
    void followUser(Follow follow);

    // 用户是否关注这个作者
    Follow isFollowed(Integer userId, Integer followId);

    // 更新关注状态 0变1 1变0
    void updateFollowStatus(Follow follow);

    // 根据用户id查询该用户的粉丝
    Integer getFansCountByUserId(Integer userId);

    // 根据用户id查询该用户的全部粉丝
    List<Follow> getFansListByUserId(Integer userId);

    // 根据用户id获取用户的关注数
    Integer getFollowCountByUserId(Integer userId);

    // 根据用户id获取该用户关注的用户
    List<Follow> getFollowListByUserId(Integer userId);
}
