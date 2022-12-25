package com.example.filemeneger_v2.client.pathHandler;

import com.example.filemeneger_v2.common.fileInfo.FileInfo;

import java.nio.file.Path;
import java.util.List;

public class PathKeeper {
    private static Path path;
    private static List<FileInfo> listFiles;

    public static Path getPath() {
        return path;
    }

    public static void setPath(Path path) {
        PathKeeper.path = path;
    }

    public static List<FileInfo> getListFiles() {
        return listFiles;
    }

    public static void setListFiles(List<FileInfo> listFiles) {
        PathKeeper.listFiles = listFiles;
    }
}
