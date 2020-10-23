package yjb.bysj.mapper;


import yjb.bysj.entity.Account;

import java.sql.Timestamp;
import java.util.List;

public interface AccountMapper {

    List<Account> searchAllAccounts();

    // 用户注册
    void userRegist(Account account);

    // 用户名是否存在
    int isUsernameExist(String username);

    // 获取该用户名的密码
    Account getAccountInfoByUsername(String username);

    // 根据id获取账号信息
    Account getAccountInfoById(Integer userId);

    // 修改账号信息
    void updateAccount(Account account);

    // 修改密码
    void updateAccountPassword(Integer userId, String newPassword);

    // 修改用户头像
    void updateAvatarByUserId(Integer userId, String avatar);
}
