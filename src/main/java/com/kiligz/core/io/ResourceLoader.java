package com.kiligz.core.io;

/**
 * 资源加载器接口
 *
 * @author Ivan
 * @date 2022/8/22 10:23
 */
public interface ResourceLoader {
    /**
     * 获取资源
     */
    Resource getResource(String location);
}
