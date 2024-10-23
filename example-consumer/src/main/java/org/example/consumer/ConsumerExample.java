package org.example.consumer;

import org.example.bootstrap.ConsumerBootstrap;
import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.proxy.ServiceProxyFactory;

/**
 * 简易消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        /*RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);*/
        //服务提供者初始化
        ConsumerBootstrap.init();

        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("oy");
        //调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        }else{
            System.out.println("user is null");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
