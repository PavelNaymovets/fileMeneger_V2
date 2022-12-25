package com.example.filemeneger_v2.client;

import com.example.filemeneger_v2.client.network.NetworkConnection;
import com.example.filemeneger_v2.client.network.NetworkReader;
import com.example.filemeneger_v2.client.network.NetworkWriter;
import com.example.filemeneger_v2.client.showAlert.AlertShower;
import com.example.filemeneger_v2.common.directorysWork.DirectoryCreator;
import com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType;
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
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType.ERROR;
import static com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType.INFORMATION;

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
    @FXML
    public Text midlText;

    private NetworkConnection networkConnection; //подключение к серверу
    private NetworkWriter networkWriter; //отправка сообщений на сервер
    private NetworkReader networkReader; //чтение сообщений от сервера
    private String hostData; //IP адрес хоста
    private int portData; //номер порта
    private static int changeScene = 0; //возврат к сцене аутентификации

    public void connectBtnAction(ActionEvent actionEvent) { //подключение к серверу (кнопка: Подключиться)
        if (host.getText().equals("") || port.getText().equals("")) {
            AlertShower.showAlert(ERROR, "Ошибка подключения к серверу.", "Не указаны IP адрес или порт хоста.");
        } else {
            hostData = host.getText(); //IP адрес хоста
            portData = Integer.parseInt(port.getText()); //номер порта

            networkConnection = NetworkConnection.getNetworkConnectionInstance();
            boolean tryConnect = networkConnection.openConnection(hostData, portData);

            if (tryConnect) { //если соединение установлено
                networkWriter = NetworkWriter.getNetworkWriterInstance();
                networkReader = NetworkReader.getNetworkReaderInstance();
                networkReader.readMessageFromServer();

                topText.setText("Вы подключены к серверу...");
                host.setDisable(true);
                port.setDisable(true);
                connectButton.setDisable(true);
                login.setDisable(false);
                password.setDisable(false);
                loginButton.setDisable(false);
                registerButton.setDisable(false);
                AuthPanelController.changeScene = 1;
                AlertShower.showAlert(INFORMATION, "Удачное подключение к серверу.", "Введите логин и пароль для входа в файловый менеджер.");
            } else {
                AlertShower.showAlert(ERROR, "Ошибка подключения к серверу.", "Неправильно указан порт или адрес хоста.");
            }
        }
    }

    //Войти в файловый менеджер
    public void loginBtnAction(ActionEvent actionEvent) {
        String log = login.getText();
        String pass = password.getText();
        String hashPassword = DigestUtils.sha256Hex(pass);

        networkWriter.sendAuthMessage(log,hashPassword); //отправляю сообщение об авторизации на сервер

//        launchMainPanel(actionEvent);

    }

    //Регистрация в системе
    public void registerBtnAction(ActionEvent actionEvent) {
        launchRegistrationPanel(actionEvent);
    }

    //Выйти из приложения
    public void quitBtnAction(ActionEvent actionEvent) {
        networkConnection.closeConnection();
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
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось войти в файловый менеджер", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //Запуск главной сцены
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Кастомизация текста
        topText.setFont(Font.font(null, FontWeight.BOLD, 15));
        midlText.setFont(Font.font(null, FontWeight.BOLD, 15));

        if (AuthPanelController.changeScene == 0) {
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
