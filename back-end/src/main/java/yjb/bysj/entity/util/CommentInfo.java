package yjb.bysj.entity.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentInfo {
    private Integer commentId;

    private Integer blogId;

    private Integer userId;

    private String userAvatar;

    private String username;

    private Integer parentId;

    private Integer toUserId;

    private String toUsername;

    private Integer toCommentId;

    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;



}
