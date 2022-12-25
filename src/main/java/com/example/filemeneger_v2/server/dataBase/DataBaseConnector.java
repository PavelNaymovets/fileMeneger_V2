package com.example.filemeneger_v2.server.dataBase;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

@Slf4j
public class DataBaseConnector implements Closeable {
    private static DataBaseConnector connectionInstance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost/usersfm";
    private String username = "root";
    private String password = "password";

    private DataBaseConnector() {
        try{
            connection = DriverManager.getConnection(url, username, password);
            log.debug("Сообщение от сервера: подключение к базе данных прошло успешно!");
        } catch(Exception e){
            log.debug("Сообщение от сервера: неудачное подключение к базе данных");
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseConnector getConnectionInstance(){
        if(connectionInstance == null) {
            connectionInstance = new DataBaseConnector();
        }

        return connectionInstance;
    }

    public Connection getConnection() {
        return connection;
    }
}
