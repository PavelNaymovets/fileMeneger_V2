package com.example.filemeneger_v2.client.user;

/**
 * Класс User описывает пользователя.
 *
 * Хранит: имя пользователя.
 *         логин пользователя.
 */

public class User {
    public String name;
    public String login;

    public User(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

}
