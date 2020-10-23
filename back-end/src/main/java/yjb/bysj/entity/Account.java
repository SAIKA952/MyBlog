package yjb.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data // 自动生成getter、setter方法，有参构造无参构造以及toString()方法
public class Account {

    private Integer id;

    private String username;

    private String password;

    private String avatar;

    private String phone;

    private Integer sex;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // @JsonFormat转换时间格式并更改时区，将"2020-09-13T08:07:44"转换为"2020-09-13 16:07:44"
    private Timestamp lastLogin;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createOn;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp updateOn;
}
