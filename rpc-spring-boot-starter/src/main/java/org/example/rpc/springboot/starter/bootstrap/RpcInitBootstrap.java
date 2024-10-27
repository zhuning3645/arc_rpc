package org.example.rpc.springboot.starter.bootstrap;

import org.example.RpcApplication;
import org.example.config.RpcConfig;
import org.example.rpc.springboot.starter.annotation.EnableRpc;
import org.example.server.tcp.VertxTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;


/**
 * RPC框架启动
 */

public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(RpcInitBootstrap.class);
    /**
     * Spring初始化时执行，初始化RPC框架
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取EnableRpc注解的属性值
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName());
        log.info("Annotation attributes: {}", annotationAttributes);


        boolean needServer = (boolean) annotationAttributes.get("needServer");

        //RPC框架初始化（配置和注册中心）
        RpcApplication.init();

        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动server");
        }
    }
}
