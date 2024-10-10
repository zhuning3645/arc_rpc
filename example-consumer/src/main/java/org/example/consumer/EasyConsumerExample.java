package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        /*//todo 需要获取UserService的实现类对象
        //调用
        User newUser = userService.getUser(user);
        */
        //静态代理
        //UserService userService = new UserServiceProxy();
        //动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("oy");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
            System.out.println(newUser.getMessage());
        } else {
            System.out.println("user == null");
        }
    }
}
