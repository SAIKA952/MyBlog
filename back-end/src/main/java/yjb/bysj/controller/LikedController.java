package yjb.bysj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.*;
import yjb.bysj.entity.util.Index;
import yjb.bysj.service.AccountService;
import yjb.bysj.service.BlogService;
import yjb.bysj.service.CommentService;
import yjb.bysj.service.LikedService;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/liked")
@CrossOrigin
public class LikedController {

    @Autowired
    private LikedService likedService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/searchAll")
    public Res searchAll() {
        List<Liked> list = likedService.searchAll();

        return Res.ok().data("likedList", list);
    }

    // 点赞
    @PostMapping("/like")
    public Res like(@RequestBody Liked liked) {

        if (null == liked.getCommentId()) { // 点赞的是博客而不是评论
            Liked blogLikedInfo = likedService.getBLogLikedInfoById(liked); // 根据博客id和用户名id查找该用户是否点赞过这篇博客
            if (null == blogLikedInfo) { // 添加点赞记录
                Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
                liked.setCreateOn(timestamp);
                liked.setStatus(1);
                likedService.like(liked);
            } else {
                likedService.updateLikedStatus(blogLikedInfo.getId()); // 取消赞或者重新点赞
            }
        } else {
            Liked commentLikedInfo = likedService.getCommentLikedInfoById(liked);
            if (null == commentLikedInfo) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
                liked.setCreateOn(timestamp);
                liked.setStatus(1);
                likedService.like(liked);
            } else {
                likedService.updateLikedStatus(commentLikedInfo.getId());
            }
        }

        return Res.ok();
    }

    // 查询点赞信息
    @GetMapping("/getLikedInfo/{blogId}/{userId}")
    public Res getLikedInfo(@PathVariable Integer blogId, @PathVariable Integer userId) {
        Liked liked = new Liked();
        liked.setBlogId(blogId);
        liked.setUserId(userId);

        Liked likedInfo = likedService.getBLogLikedInfoById(liked);

        return Res.ok().data("likedInfo", likedInfo);
    }

    // 根据博客id查询点赞博客的信息
    @GetMapping("/getBlogLikedListByBlogId/{blogId}")
    public Res getBlogLikedListByBlogId(@PathVariable Integer blogId) {

        List<Liked> list = likedService.getBlogLikedListByBlogId(blogId);

        return Res.ok().data("list", list).data("count", list.size());
    }

    // 根据用户id查询该用户点赞的文章
    @GetMapping("/getLikedListByUserId/{userId}/{page}")
    public Res getLikedListByUserId (@PathVariable Integer userId, @PathVariable Integer page) {
        List<Liked> likedList = new ArrayList<>();
        PageHelper.startPage(page, 8);
        likedList = likedService.getLikedListByUserId(userId); // 获取点赞的所有文章

        List<Index> indexList = new ArrayList<>(); // 保存博客信息

        // 根据点赞的文章id，获取文章详情
        for(int i = 0 ; i < likedList.size(); i++) {
            Blog blog = blogService.getBlogInfoById(likedList.get(i).getBlogId());
            Index index = new Index();

            // 截取字符串
            String content = blog.getContent();
            if(content.length() >= 80) {
                content = content.trim().substring(0, 80) + "...";
            }
            index.setBlogContent(content);
            index.setBlogId(blog.getId());
            index.setViews(blog.getViews());
            index.setBlogTitle(blog.getTitle());
            index.setCreateOn(likedList.get(i).getCreateOn()); // 添加点赞的时间

            // 获取评论数
            Integer commentsCount = commentService.getCommentsCountByBlogId(blog.getId());
            index.setCommentsCount(commentsCount);

            // 获取点赞数
            Integer likedCount = likedService.getLikedCountByBlogId(blog.getId());
            index.setLikedCount(likedCount);

            // 获取收藏数
            Integer collectCount = likedService.getCollectCountByBlogId(blog.getId());
            index.setCollectCount(collectCount);

            // 获取作者用户名
            Account author = accountService.getAccountInfoById(blog.getAuthorId());
            index.setAuthorName(author.getUsername());

            indexList.add(index); // 添加到list中返回
        }

        PageInfo pageInfo = new PageInfo(likedList, 5);
        pageInfo.setList(indexList);

        return Res.ok().data("pageInfo", pageInfo);
    }

    // 收藏
    @PostMapping("/collect")
    public Res collect(@RequestBody Collect collect) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
        collect.setCreateOn(timestamp);

        Collect collectInfo = likedService.getCollectInfoById(collect.getUserId(), collect.getBlogId()); // 根据用户id和博客id查找该用户是否收藏这篇博客
        if (null == collectInfo) {
            collect.setStatus(1);
            likedService.collect(collect); // 收藏
        } else {
            likedService.updateCollectStatus(collectInfo.getId());
        }

        return Res.ok();
    }

    // 根据博客id查询收藏了这篇博客的信息
    @GetMapping("getBlogCollectListByBlogId/{blogId}")
    public Res getBlogCollectListByBlogId(@PathVariable Integer blogId) {
        List<Collect> list = likedService.getBlogCollectListByBlogId(blogId);

        return Res.ok().data("list", list).data("count", list.size());
    }

    // 查询收藏信息
    @GetMapping("/isUserCollected/{blogId}/{userId}")
    public Res isCollected(@PathVariable Integer blogId, @PathVariable Integer userId) {
        Collect collect = new Collect();
        collect.setBlogId(blogId);
        collect.setUserId(userId);

        Collect collectInfo = likedService.isUserCollected(collect);

        return Res.ok().data("collectInfo", collectInfo);
    }

    // 根据用户id查找该用户收藏的文章
    @GetMapping("/getCollectListByUserId/{userId}/{page}")
    public Res getCollectListByUserId(@PathVariable Integer userId, @PathVariable Integer page) {
        List<Collect> collectList = new ArrayList<>();

        PageHelper.startPage(page, 8);
        collectList = likedService.getCollectListByUserId(userId);
        List<Index> indexList = new ArrayList<>();

        for(int i = 0; i < collectList.size(); i++) {
            Collect collect = collectList.get(i);
            Index index = new Index();

            index.setAuthorId(collect.getUserId());
            index.setBlogId(collect.getBlogId());
            index.setCreateOn(collect.getCreateOn());

            Blog blog = blogService.getBlogInfoById(collect.getBlogId());

            String content = blog.getContent();
            if (content.length() > 80) {
                content = content.substring(0, 80) + "...";
            }
            index.setBlogContent(content);
            index.setBlogTitle(blog.getTitle());
            index.setViews(blog.getViews());

            Account account = accountService.getAccountInfoById(collect.getUserId());
            index.setAuthorName(account.getUsername());

            // 获取评论数
            Integer commentsCount = commentService.getCommentsCountByBlogId(collect.getBlogId());
            index.setCommentsCount(commentsCount);

            // 获取点赞数
            Integer likedCount = likedService.getLikedCountByBlogId(collect.getBlogId());
            index.setLikedCount(likedCount);

            // 获取收藏数
            Integer collectCount = likedService.getCollectCountByBlogId(collect.getBlogId());
            index.setCollectCount(collectCount);

            indexList.add(index);
        }

        PageInfo pageInfo = new PageInfo(collectList, 5);
        pageInfo.setList(indexList);

        return Res.ok().data("pageInfo", pageInfo);
    }

    // 根据用户id获取用户收藏的文章数
    @GetMapping("/getCollectCountByUserId/{userId}")
    public Res getCollectCountByUserId(@PathVariable Integer userId) {
        Integer count = likedService.getCollectCountByUserId(userId);
        return Res.ok().data("collectCount", count);
    }

    // 根据用户id获取用户点赞的文章数
    @GetMapping("/getLikedCountByUserId/{userId}")
    public Res getLikedCountByUserId(@PathVariable Integer userId) {
        Integer count = likedService.getLikedCountByUserId(userId);
        return Res.ok().data("likedCount", count);
    }

    // 根据用户id统计该用户的所有文章获取的点赞数（主页显示）
    @GetMapping("/getAllBlogsLikedCountByUserId/{userId}")
    public Res getAllBlogsLikedCountByUserId(@PathVariable Integer userId) {
        Integer count = likedService.getAllBlogsLikedCountByUserId(userId);

        return Res.ok().data("count", count);
    }

    // 根据用户id统计该用户的所有文章获取的收藏数（主页显示）
    @GetMapping("/getAllBlogsCollectCountByUserId/{userId}")
    public Res getAllBlogsCollectCountByUserId(@PathVariable Integer userId) {
        Integer count = likedService.getAllBlogsCollectCountByUserId(userId);

        return Res.ok().data("count", count);
    }

}