package org.example.provider;

import org.example.common.model.User;
import org.example.common.service.UserService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user){
        System.out.println("用户名：" + user.getName());
        return user;
    }

    @Override
    public User getMessage(User user) {
        return null;
    }

}
