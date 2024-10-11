package org.example.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;

import java.io.IOException;

/**
 * 实现JSON序列化器
 */
public class JSONSerializer implements Serializer{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T object = objectMapper.readValue(bytes, type);
        if(object instanceof RpcRequest){
            return handleRequest((RpcRequest)object, type);
        }
        if(object instanceof RpcResponse){
            return handleResponse((RpcResponse)object, type);
        }
        return object;
    }

    /**
     * 由于Objct的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法转换成原始对象，因此这里做了处理
     * @param rpcRequest
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        //循环处理每个参数的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?>clazz = parameterTypes[i];
            //如果类型不同，则需要处理一下类型
            if(!clazz.isAssignableFrom(args[i].getClass())){
                byte[] argBytes = objectMapper.writeValueAsBytes(args[i]);
                args[i] = objectMapper.readValue(argBytes, clazz);
            }
        }
        return type.cast(rpcRequest);
    }

    /**
     * 由于Objct的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法转换成原始对象，因此这里做了处理
     * @param rpcResponse
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        //处理响应数据
        byte[] dataBytes = objectMapper.writeValueAsBytes(rpcResponse);
        rpcResponse.setData(objectMapper.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
