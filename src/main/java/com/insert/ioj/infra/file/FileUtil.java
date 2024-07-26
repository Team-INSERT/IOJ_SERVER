package com.insert.ioj.infra.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static void createDirectory(String path) throws IOException {
        Files.createDirectory(Path.of(path));
    }

    public static void saveUploadedFiles(String content, String path) throws IOException {
        try(OutputStream os = new FileOutputStream(path)) {
            os.write(content.getBytes());
        }
    }

    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Path.of(path));
    }
}
