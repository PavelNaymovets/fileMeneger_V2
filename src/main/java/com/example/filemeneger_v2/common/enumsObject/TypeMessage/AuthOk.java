package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

public class AuthOk implements AbstractMessage {
    private String name;
    private String lastName;
    private String login;

    public AuthOk(String name, String lastName, String login) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.AUTH_OK;
    }
}
