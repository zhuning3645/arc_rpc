package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        //todo 需要获取UserService的实现类对象
        UserService userService = null;
        User user = new User();
        user.setName("oy");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
