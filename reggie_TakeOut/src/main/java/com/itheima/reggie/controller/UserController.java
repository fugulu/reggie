package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.util.SMSUtils;
import com.itheima.reggie.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //如果键和值都是String类型，那么可以使用RedisTemplate的子类
    @Autowired
    private StringRedisTemplate stringredisTemplate;

    /**
     * 发短信
     * 手机号从user中获取phone属性
     */
    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        //1.获取手机号
        String phone = user.getPhone();
        //2.生成4位验证码
        String code = ValidateCodeUtils.generateValidateCode(4);
        //在控制台输出验证码
        log.info("验证码是：{}", code);
        //3.调用发短信工具类发送验证码：签名，模板编号，电话，验证码
        //SMSUtils.sendMessage("黑马旅游", "SMS_195723031", phone, code);
        //4.将验证码放到会话域中(键是：电话号码，值：验证码)
        //session.setAttribute(phone, code);

        //4.将验证码放到redis中，并且设置过期的时间(键是：唯一的电话号码，值：验证码)
        //四个参数：键、值、数量、单位
        stringredisTemplate.opsForValue().set(phone, code, 1, TimeUnit.MINUTES);

        //5.返回发送成功信息
        return R.success("发送成功");
    }

    /**
     * 登录功能
     */
    @PostMapping("login")
    public R<String> login(@RequestBody Map<String, String> param) {
        //1. 获取手机号和验证码
        String phone = param.get("phone");
        String code = param.get("code");

        //2. 判断验证码是否正确 (键是电话)
        //String genCode = (String) session.getAttribute(phone);

        //2.从redis中获取验证码
        String genCode = stringredisTemplate.opsForValue().get(phone);

        //如果值为空，说明验证码已过期
        if(genCode == null){
            return R.error("验证码已过期,请重新获取");
        }

        //3. 如果验证码错误，返回
        if (!code.equals(genCode)) {
            return R.error("验证码错误");
        }

        //4. 验证码正确调用业务层登录或注册，一定返回user对象
        User user = userService.login(phone);

        //注：键必须叫LOGIN_ID，与拦截器中一样
        session.setAttribute("LOGIN_ID", user.getId());
        //通过键来删除验证码
        stringredisTemplate.delete(phone);
        return R.success("登录成功");
    }

    /**
     * 退出功能
     */
    @PostMapping("loginout")
    public R<String> loginout() {
        session.invalidate();
        return R.success("用户退出");
    }
}
