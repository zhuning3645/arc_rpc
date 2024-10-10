package org.example.provider;

import org.example.common.service.UserService;
import org.example.registry.LocalRegistry;
import org.example.server.HttpServer;
import org.example.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        //提供web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
