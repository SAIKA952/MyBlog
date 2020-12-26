package yjb.bysj.service;


import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> searchAllAccounts();

    // 用户注册
    void userRegist(Account account);

    // 判断用户名是否重复
    boolean isUsernameExist(String username);

    // 用户登录
    Res userLogin(String username, String password);

    // 根据id获取账号信息
    Account getAccountInfoById(Integer userId);

    // 修改账号信息
    void updateAccount(Account account);

    // 修改密码
    void updateAccountPassword(Integer userId, String newPassword);

    // 修改用户头像
    void updateAvatarByUserId(Integer userId, String avatar);

    // 根据输入的内容进行查找
    List<Account> searchContent(String content);

    // 删除记录
    void deleteAccount(Integer userId);

    // 管理员登录
    Res adminLogin(String username, String password);
}
