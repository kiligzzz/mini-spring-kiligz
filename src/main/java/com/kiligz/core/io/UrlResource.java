package com.kiligz.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * url资源
 *
 * @author Ivan
 * @date 2022/8/22 10:23
 */
public class UrlResource implements Resource {
    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    /**
     * 获取资源的输入流
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = url.openConnection();
        return con.getInputStream();
    }
}
