package yjb.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

    private Integer id;

    private Integer blogId;

    private Integer userId;

    private String content;

    private Integer parentId;

    private Integer toUserId;

    private String toUsername;

    private Integer toCommentId;

    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;

}
