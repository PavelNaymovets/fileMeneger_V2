package com.example.filemeneger_v2.server.listFileHandler;

import com.example.filemeneger_v2.common.directorysWork.DirectoryCreator;
import com.example.filemeneger_v2.common.fileInfo.FileInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ListFileHandler {

    public static Path getStartPath(String login) {
        Path path = Paths.get(Paths.get("").toAbsolutePath().toString(), "cloud-storage-server", login);
        DirectoryCreator.createStartDirectory(path);

        return path;
    }

    public static List<FileInfo> getListFiles(Path path) {
        List<FileInfo> list = null;
        try {
            list = Files.list(path).map(FileInfo::new).collect(Collectors.toList());
        } catch (IOException e) {
            log.debug("Сообщение от сервера: не удалось получить список файлов");
            e.printStackTrace();
        }

        return list;
    }
}
