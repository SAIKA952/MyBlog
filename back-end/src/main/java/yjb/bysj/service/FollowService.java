package yjb.bysj.service;

import yjb.bysj.entity.Account;
import yjb.bysj.entity.Follow;

import java.util.List;

public interface FollowService {
    List<Follow> searchAll();

    void followUser(Follow follow);

    Integer isFollowed(Integer userId, Integer followId);

    void updateFollowStatus(Follow follow);

    Integer getFansCountByUserId(Integer followId);

    List<Follow> getFansListByUserId(Integer followId);

    Integer getFollowCountByUserId(Integer userId);

    List<Follow> getFollowListByUserId(Integer userId);
}
