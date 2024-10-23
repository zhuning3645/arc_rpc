package org.example.proxy;

import cn.hutool.core.collection.CollUtil;
import org.example.RpcApplication;
import org.example.config.RpcConfig;
import org.example.constant.RpcConstant;
import org.example.fault.retry.RetryStrategy;
import org.example.fault.retry.RetryStrategyFactory;
import org.example.fault.tolerant.TolerantStrategy;
import org.example.fault.tolerant.TolerantStrategyFactory;
import org.example.loadbalancer.LoadBalancer;
import org.example.loadbalancer.LoadBalancerFactory;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.model.ServiceMetaInfo;
import org.example.registry.Registry;
import org.example.registry.RegistryFactory;
import org.example.serializer.Serializer;
import org.example.serializer.SerializerFactory;
import org.example.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK动态代理
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();


        //序列化
        byte[] bodyBytes = serializer.serialize(rpcRequest);
        //从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }
        //暂时先取第一个
        //ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

        //负载均衡
        //将固定调用第一个服务节点，改为调用负载均衡器获取一个服务节点
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        //将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        //rpc请求
        RpcResponse rpcResponse;
        try {
            //发送TCP请求 使用重试机制
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
            return rpcResponse.getData();
        } catch (Exception e) {
            //throw new RuntimeException("调用失败");
            //容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}