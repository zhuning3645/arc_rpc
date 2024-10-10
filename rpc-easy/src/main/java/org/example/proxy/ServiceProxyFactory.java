package org.example.proxy;

import java.lang.reflect.Proxy;

public class ServiceProxyFactory {

    /**
     * 根据服务类获取对象
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
