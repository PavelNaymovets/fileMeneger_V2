package com.example.filemeneger_v2.server.dataBase;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DataBaseAuthenticateService {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private List<UserDataFromDataBase> userData;

    private class UserDataFromDataBase {
        private String name;
        private String lastName;
        private String email;
        private String login;
        private String password;

        public UserDataFromDataBase(String name, String lastName, String email, String login, String password) {
            this.name = name;
            this.lastName = lastName;
            this.email = email;
            this.login = login;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public DataBaseAuthenticateService(Connection connection) {
        this.connection = connection;
        this.userData = new ArrayList<>();
        updateDataUsersFromDB();
    }

    public void updateDataUsersFromDB() {
        try {
            ps = connection.prepareStatement("SELECT * FROM users");
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String login = rs.getString("login");
                String password = rs.getString("password");

                UserDataFromDataBase user = new UserDataFromDataBase(name, lastName, email, login, password);
                userData.add(user);
            }
        } catch (SQLException e) {
            log.debug("Сообщение от сервера: ошибка запроса к базе данных на получение информации о пользователе");
            e.printStackTrace();
        }
    }

    //Если по логину и паролю найдется имя и фамилия - пользователь зарегистрирован
    public String[] getNameAndLastNameByLoginAndPassword(String login, String password) {
        UserDataFromDataBase us = userData.stream()
                .filter(user -> login.equals(user.getLogin()) && password.equals(user.getPassword()))
                .findFirst()
                .orElse(null);

        if (us == null) {
            return null;
        } else {
            String name = us.getName();
            String lastName = us.getLastName();
            return new String[]{name, lastName};
        }
    }
}
