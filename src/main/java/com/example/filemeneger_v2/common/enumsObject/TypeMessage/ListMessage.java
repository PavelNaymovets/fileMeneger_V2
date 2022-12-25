package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

import com.example.filemeneger_v2.common.fileInfo.FileInfo;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ListMessage implements AbstractMessage{
    private List<FileInfo> listFiles;
    private String path;

    public ListMessage(List<FileInfo> listFiles, String path) {
        this.listFiles = listFiles;
        this.path = path;
    }

    public List<FileInfo> getListFiles() {
        return listFiles;
    }

    public String getPath() {
        return path;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.LIST_MESSAGE;
    }
}
