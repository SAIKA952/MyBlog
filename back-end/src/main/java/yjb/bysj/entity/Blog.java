package yjb.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data // 自动生成getter、setter方法，有参构造无参构造以及toString()方法
public class Blog {

    private Integer id;

    private Integer authorId;

    private String title;

    private String content;

    private Integer views;

    private Integer status;

    private Integer version;

    private Integer type;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp updateOn;
}
