package yjb.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;
import yjb.bysj.entity.Blog;
import yjb.bysj.entity.Comment;
import yjb.bysj.entity.util.CommentInfo;
import yjb.bysj.service.AccountService;
import yjb.bysj.service.BlogService;
import yjb.bysj.service.CommentService;

import java.util.*;
import java.sql.Timestamp;

@RestController
@RequestMapping("/comment")
@CrossOrigin // 解决跨域问题
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/test")
    public Res test() {

        return Res.ok().data("test", "testString");
    }

    // 发表评论
    @PostMapping("/submit")
    public Res submitComment(@RequestBody Comment comment) {
        System.out.println(comment);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
        comment.setStatus(1);
        comment.setCreateOn(timestamp);
        commentService.submitComment(comment);

        return Res.ok();
    }

    // 根据博客id，查询该博客下的所有评论
    @GetMapping("/getCommentsByBlogId/{blogId}")
    public Res getCommentsByBlogId(@PathVariable Integer blogId) {

        List<Comment> list = commentService.getCommentsByBlogId(blogId);
        List<CommentInfo> commentInfoList = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {

            // 封装数据，将数据保存在CommentInfo实体类中，方便显示信息
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.setCommentId(list.get(i).getId());
            commentInfo.setBlogId(blogId);
            commentInfo.setContent(list.get(i).getContent());
            commentInfo.setUserId(list.get(i).getUserId());
            commentInfo.setParentId(list.get(i).getParentId());
            commentInfo.setCreateOn(list.get(i).getCreateOn());

            // 如果是回复其他用户，设置回复对象的id和用户名
            Integer toUserId = list.get(i).getToUserId();
            if(toUserId != null) {
                commentInfo.setToCommentId(list.get(i).getToCommentId());
                commentInfo.setToUserId(toUserId);
                Account replyToAccount = accountService.getAccountInfoById(toUserId);
                commentInfo.setToUsername(replyToAccount.getUsername());
            }

            Account account = accountService.getAccountInfoById(list.get(i).getUserId());
            commentInfo.setUsername(account.getUsername());
            commentInfo.setUserAvatar(account.getAvatar());

            commentInfoList.add(commentInfo); // 添加到list中，返回给前端
        }

        return Res.ok().data("comments", commentInfoList);
    }

    // 根据评论id删除评论
    @PostMapping("/deleteCommentById/{commentId}")
    public Res deleteCommentById(@PathVariable Integer commentId) {
        commentService.deleteCommentById(commentId);

        return Res.ok();
    }


}