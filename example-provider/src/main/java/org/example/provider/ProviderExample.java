package org.example.provider;

import org.example.RpcApplication;
import org.example.common.service.UserService;
import org.example.config.RpcConfig;
import org.example.model.ServiceMetaInfo;
import org.example.registry.LocalRegistry;
import org.example.registry.Registry;
import org.example.registry.RegistryConfig;
import org.example.registry.RegistryFactory;
import org.example.server.tcp.VertxTcpServer;

public class ProviderExample {


    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        String servicename = UserService.class.getName();
        LocalRegistry.register(servicename, UserServiceImpl.class);

        //注册到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(servicename);
        serviceMetaInfo.setServiceVersion(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try{
            registry.register(serviceMetaInfo);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        //提供web服务 改成tcp启动器
        /*HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());*/
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8081);
    }
}
