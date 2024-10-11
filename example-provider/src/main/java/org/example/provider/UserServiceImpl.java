package org.example.provider;

import org.example.common.model.User;
import org.example.common.service.UserService;

/**
 * 用户服务实现类
 * 具体远程方法的实现逻辑
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user){
        System.out.println("用户名：" + user.getName());
        return user;
    }

    @Override
    public User getMessage(User user) {
        System.out.println("message is : " + user.getMessage());
        return user;
    }

    @Override
    public String addUser(User user) {
        String part1 = "add用户名：";
        System.out.println(part1 + user.getName());
        return part1 + " " +  user.getName();
    }

}
