package org.example.spi;

import jdk.jpackage.internal.Log;
import org.example.serializer.Serializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.util.ServiceLoaderUtil.load;

/**
 * SPI加载器（支持键值对映射
 */
public class SpiLoader {

    /**
     * 存储已加载的类： 接口名 => (key=>实现类）
     */
    private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();


    /**
     * 对象实例缓存（避免重复new）,类路径=>对象实例，单例模式
     */
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 系统SPI目录
     */
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system";

    /**
     * 用户自定义SPI目录
     */
    private static final String PRC_CUSTOM_SPI_DIR = "META-INF/rpc/custom";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DISRS = new String[]{RPC_SYSTEM_SPI_DIR, PRC_CUSTOM_SPI_DIR};

    /**
     * 动态加载的类列表
     */
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    /**
     * 加载所有类型
     */
    public static void loadAll() {
        Log.info("加载所有SPI");
        for(Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }

    /**
     * 获得某个接口的示例
     * @param tClass
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T getInstance(Class<?> tClass , String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader未加载 %s类型", tClassName));
        }
        if(!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader的 %s 不存在 key=%s", tClassName, key));
        }
        //获取到要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);
        //从实例缓存中加载指定类型的实例
        String implClassName = implClass.getName();
        if(!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, tClass.newInstance());
            }catch (InstantiationException | IllegalAccessException e) {
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            }
        }
        return (T)instanceCache.get(implClassName);
    }
}