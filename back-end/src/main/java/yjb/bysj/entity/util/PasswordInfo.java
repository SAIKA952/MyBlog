package yjb.bysj.entity.util;

import lombok.Data;

// 用于修改密码的实体类
@Data
public class PasswordInfo {

    private Integer userId;

    private String oldPassword;

    private String newPassword;
}

