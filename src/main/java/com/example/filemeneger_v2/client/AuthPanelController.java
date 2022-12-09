package com.example.filemeneger_v2.client;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthPanelController implements Initializable {

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
    public Text midlText;

    private ObjectEncoderOutputStream os;
    private ObjectDecoderInputStream is;

    public static int changeScene = 0 ; //возврат к сцене аутентификации

    //Поключиться к серверу
    public void connectBtnAction(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(host.getText(), Integer.parseInt(port.getText()));
            this.os = new ObjectEncoderOutputStream(socket.getOutputStream());
            this.is = new ObjectDecoderInputStream(socket.getInputStream());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удачное подключение к серверу. Введите логин и пароль", ButtonType.OK);
            alert.showAndWait();
            topText.setText("Вы подключены к серверу...");
            host.setDisable(true);
            port.setDisable(true);
            connectButton.setDisable(true);
            login.setDisable(false);
            password.setDisable(false);
            loginButton.setDisable(false);
            registerButton.setDisable(false);
            AuthPanelController.changeScene = 1;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подключиться к серверу", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //Войти в файловый менеджер
    public void loginBtnAction(ActionEvent actionEvent) {
        launchMainPanel(actionEvent);
    }

    //Регистрация в системе
    public void registerBtnAction(ActionEvent actionEvent) {
        launchRegistrationPanel(actionEvent);
    }

    //Выйти из приложения
    public void quitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    //Переключиться на окно регистрации
    public void launchRegistrationPanel(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registrationPanel.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 300, 350));
            stage.setTitle("Регистрация");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось вернуться к окну регистрации", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //Переключиться на окно файлового менеджера
    public void launchMainPanel(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fileManagerPanel.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 600));
            stage.setTitle("Файловый менеджер");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось войти в файловый менеджер", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //Запуск главной сцены
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topText.setFont(Font.font(null, FontWeight.BOLD, 15));
        midlText.setFont(Font.font(null, FontWeight.BOLD, 15));
        if(AuthPanelController.changeScene == 0) {
            login.setDisable(true);
            password.setDisable(true);
            loginButton.setDisable(true);
            registerButton.setDisable(true);
        } else {
            host.setDisable(true);
            port.setDisable(true);
            connectButton.setDisable(true);
            login.setDisable(false);
            password.setDisable(false);
            loginButton.setDisable(false);
            registerButton.setDisable(false);
        }
    }
}
