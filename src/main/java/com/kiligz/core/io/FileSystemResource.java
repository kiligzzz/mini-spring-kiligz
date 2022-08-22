package com.kiligz.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件系统下的资源
 *
 * @author Ivan
 * @date 2022/8/22 10:24
 */
public class FileSystemResource implements Resource {
    private final String filePath;

    public FileSystemResource(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取资源的输入流
     */
    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path path = Paths.get(filePath);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
