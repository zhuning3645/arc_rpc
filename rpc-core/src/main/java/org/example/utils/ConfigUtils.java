package org.example.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
/**
 * 配置工具类
 */
public class ConfigUtils {
    /**
     * todo:支持读取yml yaml等不同格式的配置文件
     * todo:支持监听配置文件的变更，并自动更新配置对象
     * 参考思路：使用Hutool工具类 props.autoLoad()可以实现配置文件变更的监听和自动加载
     * todo:配置文件支持中文，需要注意编码问题
     * todo:配置项需要分组，随着配置项的增多，可以考虑对配置项进行分组
     */

    /**
     * 加载配置对象
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}
