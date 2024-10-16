package org.example.registry;

import org.example.model.ServiceMetaInfo;

import java.util.List;

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
}
