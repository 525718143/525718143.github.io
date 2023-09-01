package ptumall.service;

import org.springframework.stereotype.Service;
import ptumall.model.User;

@Service
public interface UserService {
    //注册业务
    User registerService(User user);
    //登录业务
    User loginService(String uname,String upassword);
}
