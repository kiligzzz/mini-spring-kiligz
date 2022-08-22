package com.kiligz.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源的抽象和访问接口
 *
 * @author Ivan
 * @date 2022/8/22 10:23
 */
public interface Resource {
    /**
     * 获取资源的输入流
     */
    InputStream getInputStream() throws IOException;
}
