package com.oy.ourpc.protocol;

import org.example.fault.retry.NoRetryStrategy;
import org.example.fault.retry.RetryStrategy;
import org.example.model.RpcResponse;
import org.junit.Test;

/**
 * 重试策略测试
 */
public class RetryStrategyTest {

    RetryStrategy retrystrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try{
            RpcResponse rpcResponse = retrystrategy.doRetry(()->{
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        }catch (Exception e){
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
