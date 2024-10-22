package org.example.loadbalancer;

import org.example.model.ServiceMetaInfo;
import org.example.utils.MurmurHash3;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性哈希负债均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer{

    /**
     * 一致性Hash环，存放虚拟节点
     */
    private final TreeMap<Integer, ServiceMetaInfo>virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODE_SIZE = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            return null;
        }

        //构建虚拟节点环
        for(ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList){
            for(int i = 0 ; i < VIRTUAL_NODE_SIZE ; i++){
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }

        //获取调用请求的hash值
        int hash = getHash(requestParams);

        //选择最接近且大于等于调用请求hash值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.floorEntry(hash);
        if(entry == null){
            //如果没有大于等于调用请求hash值的虚拟节点，则返回环首部的节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * Hash算法 可以根据自己的需求进行扩展
     * 这里使用MurmurHash算法实现
     * @param key
     * @return
     */
    private int getHash(Object key){
        if(key instanceof String){
            return MurmurHash3.hash((String)key);
        }else{
            return key.hashCode();
        }
    }

}
