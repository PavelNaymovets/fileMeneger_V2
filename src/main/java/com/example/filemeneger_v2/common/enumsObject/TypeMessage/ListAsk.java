package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

import java.nio.file.Path;

public class ListAsk implements AbstractMessage{
    private String login;
    private Path path;

    public ListAsk(String login, Path path) {
        this.login = login;
        this.path = path;
    }

    public String getLogin() {
        return login;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.LIST_ASK;
    }
}
