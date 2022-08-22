package com.kiligz.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认的资源加载器
 *
 * @author Ivan
 * @date 2022/8/22 10:23
 */
public class DefaultResourceLoader implements ResourceLoader {
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 根据location获取资源
     */
    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // classpath下的资源
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 尝试当成url处理
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 当成文件系统下的资源处理
                return new FileSystemResource(location);
            }
        }
    }
}
