package ptumall.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptumall.dao.UserDao;
import ptumall.model.User;
import ptumall.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserDao userDao;

    @Override
    public User registerService(User user) {
        User tmp = userDao.findByUname(user.getUname());
        if (tmp != null){
            return null;
        }else {
            userDao.register(user);
            return user;
        }

    }

    @Override
    public User loginService(String uname, String upassword) {
       return userDao.login(uname,upassword);
    }
}
