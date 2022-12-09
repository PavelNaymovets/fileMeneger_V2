package com.example.filemeneger_v2.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("authPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 300);
        stage.setTitle("Аутентификация");
        stage.setScene(scene);
        stage.setX(10);
        stage.setY(10);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}