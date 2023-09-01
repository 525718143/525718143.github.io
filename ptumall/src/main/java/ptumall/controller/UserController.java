package ptumall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptumall.model.User;
import ptumall.service.UserService;
import ptumall.service.impl.UserServiceImpl;
import ptumall.utils.JWTUtils;
import ptumall.utils.Result;
import ptumall.utils.ResultCodeEnum;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //注册接口
    @RequestMapping("/register")
    public Result<User> register(@RequestBody User user){
        if(userService.registerService(user) != null){
            return Result.success(user);
        }else {
            return Result.failure(ResultCodeEnum.USER_IS_EXITES);
        }
    }

    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        User userformJdbc = userService.loginService(user.getUname(), user.getUpassword());
        if (userformJdbc == null){
            return Result.failure(ResultCodeEnum.UNAUTHORIZED,"用户名或密码错误");

        }else {
            //登录成功，返回token信息
            String token = JWTUtils.getToken(user.getUaccount(), user.getUname());
            Map<String, String> userMap = new HashMap<>();
            userMap.put("userId",userformJdbc.getUaccount());
            userMap.put("userName",userformJdbc.getUname());
            userMap.put("token",token);
            return Result.success(userMap);
        }

    }
}
