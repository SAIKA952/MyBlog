package yjb.bysj.controller;

import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.RandomUtil;
import yjb.bysj.common_utils.Res;
import yjb.bysj.service.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sendMsg")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/validate/{code}/{phone}")
    public Res validate(@PathVariable String code, @PathVariable String phone) {

        String valiCode = redisTemplate.opsForValue().get(phone);
        if (valiCode.equals(code)) {
            return Res.ok();
        }
        return Res.error().code(20006).message("验证码输入错误，请重试");
    }

    // 发送短信的方法
    @GetMapping("/send/{phone}")
    public Res sendMsm(@PathVariable String phone) {
        // 先从redis中获取验证码，如果能获取到就直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return Res.ok();
        }

        // 如果redis中获取不到，就进行阿里云发送
        // 生成随机的值，传到阿里云中进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        //调用service发送短信的方法
        boolean isSend = messageService.send(param, phone);
        if(isSend) {
            // 发送成功，把发送成功的验证码放到redis里，同时设置有效时间
            // 参数分别为key，value，时间，时间的单位
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Res.ok();
        } else {
            return Res.error().message("短信发送失败");
        }
    }


}
