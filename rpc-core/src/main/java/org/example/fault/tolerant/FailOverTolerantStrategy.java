package org.example.fault.tolerant;

import lombok.extern.slf4j.Slf4j;
import org.example.model.RpcResponse;

import java.util.Map;

/**
 * 转移到其他服务节点
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {


    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //todo 获取其他服务节点并调用
        return null;
    }
}
