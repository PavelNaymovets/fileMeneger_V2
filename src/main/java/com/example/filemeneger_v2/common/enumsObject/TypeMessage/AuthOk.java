package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

public class AuthOk implements AbstractMessage {
    private String userName;
    private String login;

    public String getUserName() {
        return userName;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.AUTH_OK;
    }
}
