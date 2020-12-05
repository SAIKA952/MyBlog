package yjb.bysj.entity.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

// 专门用作首页数据的实体类
@Data
public class Index {

    // 作者id
    private Integer authorId;

    // 作者用户名
    private String authorName;

    // 作者头像
    private String authorAvatar;

    // 浏览数
    private Integer views;

    // 博客id
    private Integer blogId;

    // 博客内容
    private String blogContent;

    // 博客状态  -2正在审核 -1删除 0未发布（草稿） 1已发布
    private Integer blogStatus;

    // 博客标题
    private String blogTitle;

    // 点赞数
    private Integer likedCount;

    // 评论数
    private Integer commentsCount;

    // 收藏数
    private Integer collectCount;

    // 博客创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;

}
