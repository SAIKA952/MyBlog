package yjb.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

// 点赞表的实体类
@Data
public class Liked {

    private Integer id;

    private Integer userId;

    private Integer blogId;

    private Integer commentId;

    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;
}
