package org.example.loadbalancer;

import org.example.spi.SpiLoader;

/**
 * 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancerFactory.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancerFactory DEFAULT_LOAD_BALANCER = new LoadBalancerFactory();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancerFactory.class, key);
    }


}
