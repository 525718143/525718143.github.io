package ptumall.dao;

import org.apache.ibatis.annotations.Mapper;
import ptumall.model.User;

@Mapper
public interface UserDao {
    //注册
    int register(User user);
    //判断用户是否重名
    User findByUname(String uname);

    //登录
    User login(String uname,String upassword);
}
