package org.example.registry;

import org.example.model.ServiceMetaInfo;

import java.util.List;

//todo zookeeper注册中心还没有实现
public class ZooKeeperRegistry implements Registry{

    @Override
    public void init(RegistryConfig registryConfig) {

    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {

    }

    @Override
    public void unregister(ServiceMetaInfo serviceMetaInfo) {

    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        return List.of();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void heartBeat() {

    }

    @Override
    public void watch(String serviceNodeKey) {

    }
}
