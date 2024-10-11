package org.example.consumer;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.serializer.JdkSerializer;
import org.example.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理
 */
public class UserServiceProxy implements UserService {

    public User getUser(User user){
        //指定序列化器
        Serializer serializer = new JdkSerializer();

        //构建要发送的rpcRequest对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        //序列化请求并发送
        try {
            //将要发送的请求序列化成字节数组bodyBytes
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()){
                result = httpResponse.bodyBytes();
            }
            //从 RpcResponse 中获取数据并将其转换为 User 对象返回
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User)rpcResponse.getData();
        }catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("调用完成");
        return null;
    }

    @Override
    public User getMessage(User user) {

        return null;
    }

    @Override
    public String addUser(User user) {
        return null;
    }
}
