package org.example.consumer;

import org.example.config.RpcConfig;
import org.example.utils.ConfigUtils;

/**
 * 简易消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
