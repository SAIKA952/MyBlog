package yjb.bysj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjb.bysj.entity.Account;
import yjb.bysj.entity.Follow;
import yjb.bysj.mapper.FollowMapper;
import yjb.bysj.service.FollowService;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<Follow> searchAll() {
        List<Follow> list = followMapper.searchAll();
        return list;
    }

    @Override
    public void followUser(Follow follow) {
        followMapper.followUser(follow);
    }

    @Override
    public Integer isFollowed(Integer userId, Integer followId) {
        Follow follow = followMapper.isFollowed(userId, followId);

        Integer isFollow = null;
        if (null == follow) {
            isFollow = -1;
        } else {
            isFollow = follow.getStatus();
        }

        return isFollow;
    }

    @Override
    public void updateFollowStatus(Follow follow) {
        followMapper.updateFollowStatus(follow);
    }

    @Override
    public Integer getFansCountByUserId(Integer followId) {
        Integer count = followMapper.getFansCountByUserId(followId);
        return count;
    }

    @Override
    public List<Follow> getFansListByUserId(Integer followId) {
        List<Follow> list = followMapper.getFansListByUserId(followId);
        return list;
    }

    @Override
    public Integer getFollowCountByUserId(Integer userId) {
        Integer count = followMapper.getFollowCountByUserId(userId);
        return count;
    }

    @Override
    public List<Follow> getFollowListByUserId(Integer userId) {
        List<Follow> followList = followMapper.getFollowListByUserId(userId);
        return followList;
    }
}
