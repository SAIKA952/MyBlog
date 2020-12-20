package yjb.bysj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.JwtUtils;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;
import yjb.bysj.entity.util.FollowInfo;
import yjb.bysj.entity.util.PasswordInfo;
import yjb.bysj.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RestController // 相当于@Controller和@ResponseBody两个注解
@RequestMapping("/account")
@CrossOrigin // 解决跨域问题
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("test")
    public String test() {
        return "OK!!!!!";
    }

    @GetMapping("/searchAll")
    public List<Account> searchAll() {
        List<Account> list = accountService.searchAllAccounts();
        return list;
    }

    // 用户注册
    @PostMapping("/regist")
    public Res userRegist(@RequestBody Account account) {
        // 先判断用户名是否存在，如果不存在再进行注册
        if (!accountService.isUsernameExist(account.getUsername())) {
            // 注册账号
            accountService.userRegist(account);
            return Res.ok();
        }
        // 设置一个状态码
        return Res.error().code(20001);
    }

    // 用户登录
    @PostMapping("/login")
    public Res userLogin(@RequestBody Account account){

        String username = account.getUsername();
        String password = account.getPassword();
        Res res = accountService.userLogin(username, password);

        return res;
    }

    // 根据token获取账号id，根据账号id获取账号信息
    @GetMapping("/getAccountInfoByToken")
    public Res getAccountInfoByToken(HttpServletRequest request){
        // 获取账号id
        String stringId = JwtUtils.getMemberIdByJwtToken(request);
        Integer userId = Integer.parseInt(stringId);

        // 根据id获取用户信息
        Account accountInfo = accountService.getAccountInfoById(userId);

        return Res.ok().data("accountInfo", accountInfo);
    }

    // 根据id查询用户信息
    @GetMapping("/getAccountInfoById/{id}")
    public Res getAccountInfoById(@PathVariable Integer id) {
        Account account = accountService.getAccountInfoById(id);

        return Res.ok().data("account", account);
    }

    // 根据id查询用户信息
    @GetMapping("/getAccountById/{id}")
    public Account getAccountById(@PathVariable Integer id) {
        Account account = accountService.getAccountInfoById(id);

        return account;
    }

    // 修改用户资料
    @PostMapping("/updateAccount")
    public Res updateAccount(@RequestBody Account account) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setUpdateOn(timestamp);
        accountService.updateAccount(account);

        return Res.ok();
    }

    // 修改用户密码
    @PostMapping("/updatePassword")
    public Res updatePassword(@RequestBody PasswordInfo passwordInfo) {
        // 根据用户id查询出用户信息
        Account account = accountService.getAccountInfoById(passwordInfo.getUserId());

        if (passwordInfo.getOldPassword().equals(account.getPassword())) { // 输入的旧密码是正确的
            if (passwordInfo.getOldPassword().equals(passwordInfo.getNewPassword())) { // 旧密码不能与新密码相同
                return Res.error().code(21001).message("旧密码与新密码不能相同");
            } else {
                accountService.updateAccountPassword(passwordInfo.getUserId(), passwordInfo.getNewPassword());
                return Res.ok();
            }
        } else { // 输入的密码不正确
            return Res.error().code(21002).message("密码错误，请重新输入");
        }
    }

    // 用户修改头像
    @PostMapping("/updateAvatar")
    public Res updateAvatar(@RequestBody FollowInfo followInfo) {
        Integer userId = followInfo.getUserId();
        String avatar = followInfo.getAvatar();
        accountService.updateAvatarByUserId(userId, avatar);
        return Res.ok();
    }

    // ==============================================后台管理部分=====================================================================

    // 查找所有账号
    @GetMapping("/searchAllAccount/{page}")
    public Res searchAllCount(@PathVariable Integer page) {

        PageHelper.startPage(page, 8);
        List<Account> list = accountService.searchAllAccounts();
        PageInfo pageInfo = new PageInfo(list, 5);

        return Res.ok().data("pageInfo", pageInfo);
    }

    // 查找内容（分页）
    @GetMapping("/searchContent/{content}")
    public Res searchContent(@PathVariable String content) {
        PageHelper.startPage(1, 8);
        List<Account> list = accountService.searchContent(content);
        PageInfo pageInfo = new PageInfo(list, 5);

        return Res.ok().data("pageInfo", pageInfo);
    }

    // 根据用户id删除数据库中的账号记录
    @GetMapping("/deleteAccount/{userId}")
    public Res deleteAccount(@PathVariable Integer userId) {
        accountService.deleteAccount(userId);
        return Res.ok();
    }


}
