package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 *
 * 调用的方法名字是getUser + addUser
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        //静态代理
        //UserService userService = new UserServiceProxy();
        //动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("oy");
        // 调用
        User newUser = userService.getUser(user);//查看原始的名字
        String newUser2 =userService.addUser(user);//调用远程方法对传入的user进行修改
        if (newUser != null) {
            System.out.println("newUser：" + newUser.getName());
            System.out.println("newUser2：" + newUser2);
        } else {
            System.out.println("user == null");
        }
    }
}
