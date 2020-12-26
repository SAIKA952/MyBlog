package yjb.bysj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjb.bysj.common_utils.JwtUtils;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;
import yjb.bysj.mapper.AccountMapper;
import yjb.bysj.service.AccountService;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> searchAllAccounts() {
        List<Account> list = accountMapper.searchAllAccounts();
        return list;
    }

    // 用户注册
    @Override
    public void userRegist(Account account) {

        // 将当前时间传入数据库字段create/update_on
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setAvatar("https://edu-952.oss-cn-hangzhou.aliyuncs.com/default.jpg"); // 设置默认头像
        account.setCreateOn(timestamp);
        account.setUpdateOn(timestamp);
        account.setStatus(0); // 设置账号状态
        account.setPermission(0);
//        System.out.println(account);
        accountMapper.userRegist(account);
    }

    @Override
    public boolean isUsernameExist(String username) {
        // 返回该用户名在数据库中有几条数据
        int i = accountMapper.isUsernameExist(username);
        if (i == 0) {
            // 数据库中不存在该用户名
            return false;
        }
        // 存在
        return true;
    }

    // 用户登录
    @Override
    public Res userLogin(String username, String password) {
//        判断用户名是否存在
        if(accountMapper.isUsernameExist(username) == 0) {
//            设置状态码20002表示输入的用户名不存在
            return Res.error().code(20002);
        }

        // 得到数据库中该用户名的密码
        Account accountInfoByUsername = accountMapper.getAccountInfoByUsername(username);

        if(accountInfoByUsername.getPassword().equals(password)) {

            // 将根据用户id和用户名生成token字符串
            String jwtToken = JwtUtils.getJwtToken(accountInfoByUsername.getId().toString(), accountInfoByUsername.getUsername());
//            System.out.println(jwtToken);

            // 设置最近登录时间
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            accountMapper.updateLastLogin(accountInfoByUsername.getId(), timestamp);

            return Res.ok().data("accountToken", jwtToken);
        }

//        密码不正确的状态码
        return Res.error().code(20003);
    }

    // 根据id获取账号信息
    @Override
    public Account getAccountInfoById(Integer userId) {
        Account account = accountMapper.getAccountInfoById(userId);
        return account;
    }

    @Override
    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
    }

    @Override
    public void updateAccountPassword(Integer userId, String newPassword) {
        accountMapper.updateAccountPassword(userId, newPassword);
    }

    @Override
    public void updateAvatarByUserId(Integer userId, String avatar) {
        accountMapper.updateAvatarByUserId(userId, avatar);
    }

    @Override
    public List<Account> searchContent(String content) {
        List<Account> list = accountMapper.searchContent(content);
        return list;
    }

    @Override
    public void deleteAccount(Integer userId) {
        accountMapper.deleteAccount(userId);
    }

    @Override
    public Res adminLogin(String username, String password) {
//        判断用户名是否存在
        if(accountMapper.isUsernameExist(username) == 0) {
//            设置状态码20002表示输入的用户名不存在
            return Res.error().code(20002);
        }

        Account account = accountMapper.getAccountInfoByUsername(username);
        if (account.getPassword().equals(password)) {

            if (account.getPermission() == 2) {
                return Res.ok().data("account", account);
            } else {
                // 20005没有登录权限
                return Res.error().code(20005);
            }

        }
        //        密码不正确的状态码
        return Res.error().code(20003);
    }

}
