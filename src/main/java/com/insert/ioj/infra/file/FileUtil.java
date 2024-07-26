package com.insert.ioj.infra.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static void createDirectory(String path) throws IOException {
        Files.createDirectory(Path.of(path));
    }

    public static void saveUploadedFiles(String content, String path) throws IOException {
        try(OutputStream os = new FileOutputStream(path)) {
            os.write(content.getBytes());
        }
    }

    public static void copyFile(String src, String dest) throws IOException {
        Path copied = Paths.get(dest);
        Path originalPath = Paths.get(src);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Path.of(path));
    }
}
