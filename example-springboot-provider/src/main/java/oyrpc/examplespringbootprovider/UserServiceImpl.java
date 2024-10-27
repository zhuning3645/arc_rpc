package oyrpc.examplespringbootprovider;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }

    @Override
    public User getMessage(User user) {
        return null;
    }

    @Override
    public String addUser(User user) {
        return "";
    }
}
