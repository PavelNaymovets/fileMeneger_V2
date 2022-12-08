package com.example.filemeneger_v2.client;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    public Text topText;
    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public Button connectButton;
    @FXML
    public TextField login;
    @FXML
    public TextField password;
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;

    private ObjectEncoderOutputStream os;
    private ObjectDecoderInputStream is;

    public void connectBtnAction(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(host.getText(), Integer.parseInt(port.getText()));
            this.os = new ObjectEncoderOutputStream(socket.getOutputStream());
            this.is = new ObjectDecoderInputStream(socket.getInputStream());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удачное подключение к серверу. Введите логин и пароль", ButtonType.OK);
            alert.showAndWait();
            host.setDisable(true);
            port.setDisable(true);
            connectButton.setDisable(true);
            login.setDisable(false);
            password.setDisable(false);
            loginButton.setDisable(false);
            registerButton.setDisable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подключиться к серверу", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void loginBtnAction(ActionEvent actionEvent) {
    }

    public void registerBtnAction(ActionEvent actionEvent) {
    }

    public void quitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setDisable(true);
        password.setDisable(true);
        loginButton.setDisable(true);
        registerButton.setDisable(true);
    }
}
