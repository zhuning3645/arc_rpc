package com.oy.oyrpc.registry;

import org.example.model.ServiceMetaInfo;
import org.example.registry.EtcdRegistry;
import org.example.registry.Registry;
import org.example.registry.RegistryConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RegistryTest {

    final Registry registry = new EtcdRegistry();

    @Before
    public void init(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setRegistry("http://localhost:2379");
        registry.init(registryConfig);
    }

    @Test
    public void registry() throws Exception{
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService_test");
        serviceMetaInfo.setServiceVersion("1.0.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService_test");
        serviceMetaInfo.setServiceVersion("1.0.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1235);
        registry.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService_test");
        serviceMetaInfo.setServiceVersion("2.0.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.register(serviceMetaInfo);

    }

    @Test
    public void unRegistry() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService_test");
        serviceMetaInfo.setServiceVersion("1.0.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.unregister(serviceMetaInfo);
    }

    @Test
    public void serviceDiscovery(){
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService_test");
        serviceMetaInfo.setServiceVersion("1.0.0");
        String serviceKey = serviceMetaInfo.getServiceKey();
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
        System.out.println(serviceMetaInfoList);
    }
}
