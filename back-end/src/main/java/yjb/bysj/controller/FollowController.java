package yjb.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;
import yjb.bysj.entity.Follow;
import yjb.bysj.entity.util.FollowInfo;
import yjb.bysj.service.AccountService;
import yjb.bysj.service.BlogService;
import yjb.bysj.service.FollowService;
import yjb.bysj.service.LikedService;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/follow")
@CrossOrigin
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private LikedService likedService;

    @GetMapping("/searchAll")
    public Res searchAll() {
        List<Follow> list = followService.searchAll();

        return Res.ok().data("list", list);
    }

    // 关注用户
    @PostMapping("/followUser")
    public Res followUser(@RequestBody Follow follow) {

        Integer isFollow = followService.isFollowed(follow.getUserId(), follow.getFollowId());
        if (isFollow == -1) {
            // 获取当前时间
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            follow.setCreateOn(timestamp);
            follow.setStatus(1);
            followService.followUser(follow);
        } else {
            followService.updateFollowStatus(follow);
        }

        return Res.ok();
    }

    // 根据用户id和作者id查看该用户是否有关注这个作者
    @GetMapping("/isFollowed/{userId}/{followId}")
    public Res isFollowed(@PathVariable Integer userId, @PathVariable Integer followId) {
        Integer isFollow = followService.isFollowed(userId, followId);
        if (isFollow == -1) isFollow = 0;

        return Res.ok().data("isFollowed", isFollow);
    }

    // 根据用户id查询该用户的粉丝数
    @GetMapping("/getFansByUserId/{followId}")
    public Res getFansByUserId(@PathVariable Integer followId) {
        Integer count = followService.getFansCountByUserId(followId);

        return Res.ok().data("count", count);
    }

    // 根据用户id查询该用户的关注数
    @GetMapping("/getFollowCountByUserId/{userId}")
    public Res getFollowCountByUserId(@PathVariable Integer userId) {
        Integer count = followService.getFollowCountByUserId(userId);

        return Res.ok().data("count", count);
    }

    // 根据用户id查询该用户的全部粉丝信息
    @GetMapping("/getFansListByUserId/{userId}")
    public Res getFansListByUserId(@PathVariable Integer userId) {
        List<Follow> followingList = followService.getFansListByUserId(userId);
        List<FollowInfo> fansInfoList = new ArrayList<>();

        for (int i = 0; i < followingList.size(); i++) {
            Follow follow = followingList.get(i);
            FollowInfo followInfo = new FollowInfo(); // 封装显示数据

            Account account = accountService.getAccountInfoById(follow.getUserId());
            if (null != account) {
                followInfo.setUsername(account.getUsername());
                followInfo.setAvatar(account.getAvatar());
                followInfo.setUserId(follow.getUserId());
                followInfo.setFollowId(follow.getFollowId());

                // 获取该用户点赞文章的数量
                Integer likedCount = likedService.getLikedCountByUserId(follow.getUserId());
                followInfo.setLikedCount(likedCount);

                // 获取该用户收藏文章的数量
                Integer collectCount = likedService.getCollectCountByUserId(follow.getUserId());
                followInfo.setCollectCount(collectCount);

                // 获取该用户写的文章的数量
                Integer blogCount = blogService.getBlogCountByUserId(follow.getUserId());
                followInfo.setBlogCount(blogCount);

                // 获取该用户的粉丝数
                Integer fansCount = followService.getFansCountByUserId(follow.getUserId());
                followInfo.setFansCount(fansCount);

                // 获取该用户的关注数
                Integer followCount = followService.getFollowCountByUserId(follow.getUserId());
                followInfo.setFollowCount(followCount);

                fansInfoList.add(followInfo);
            }
        }

        return Res.ok().data("fansInfoList", fansInfoList).data("fansCount", fansInfoList.size());
    }

    // 根据用户id获取该用户关注的所有用户信息
    @GetMapping("/getFollowListByUserId/{userId}")
    public Res getFollowListByUserId(@PathVariable Integer userId) {
        List<Follow> followList = followService.getFollowListByUserId(userId);
        List<FollowInfo> followInfoList = new ArrayList<>();

        for(int i = 0; i < followList.size(); i++) {
            Follow follow = followList.get(i);
            FollowInfo followInfo = new FollowInfo(); // 封装显示数据

            Account account = accountService.getAccountInfoById(follow.getFollowId());
            followInfo.setUsername(account.getUsername());
            followInfo.setAvatar(account.getAvatar());
            followInfo.setFollowId(follow.getFollowId());
            followInfo.setUserId(follow.getUserId());

            // 获取该用户点赞文章的数量
            Integer likedCount = likedService.getLikedCountByUserId(follow.getFollowId());
            followInfo.setLikedCount(likedCount);

            // 获取该用户收藏文章的数量
            Integer collectCount = likedService.getCollectCountByUserId(follow.getFollowId());
            followInfo.setCollectCount(collectCount);

            // 获取该用户写的文章的数量
            Integer blogCount = blogService.getBlogCountByUserId(follow.getFollowId());
            followInfo.setBlogCount(blogCount);

            // 获取该用户的粉丝数
            Integer fansCount = followService.getFansCountByUserId(follow.getFollowId());
            followInfo.setFansCount(fansCount);

            // 获取该用户的关注数
            Integer followCount = followService.getFollowCountByUserId(follow.getFollowId());
            followInfo.setFollowCount(followCount);

            followInfoList.add(followInfo);
        }

        return Res.ok().data("followInfoList", followInfoList).data("followCount", followInfoList.size());
    }


}
