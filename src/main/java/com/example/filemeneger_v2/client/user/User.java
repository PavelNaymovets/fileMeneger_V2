package com.example.filemeneger_v2.client.user;

/**
 * Класс User описывает пользователя.
 *
 * Хранит: имя пользователя.
 *         логин пользователя.
 */

public class User {
    public String name;
    public String lastName;

    public String login;

    public User(String name, String lastname, String login) {
        this.name = name;
        this.lastName = lastname;
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

}
