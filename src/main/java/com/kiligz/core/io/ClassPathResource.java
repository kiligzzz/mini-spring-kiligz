package com.kiligz.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath下的资源
 *
 * @author Ivan
 * @date 2022/8/22 10:24
 */
public class ClassPathResource implements Resource {
    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    /**
     * 获取资源的输入流
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        if (is == null)
            throw new FileNotFoundException(path + " cannot be opened because it does not exist.");
        return is;
    }
}
