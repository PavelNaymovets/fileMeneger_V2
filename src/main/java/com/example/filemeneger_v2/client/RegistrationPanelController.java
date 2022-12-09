package com.example.filemeneger_v2.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationPanelController implements Initializable {
    @FXML
    public TextField regLastNameField;
    @FXML
    public TextField regNameField;
    @FXML
    public TextField regEmailField;
    @FXML
    public TextField regLoginField;
    @FXML
    public PasswordField regPasswordField;
    @FXML
    public PasswordField regConfirmPasswordField;
    @FXML
    public Button regRegisterButton;
    @FXML
    public Button regCloseButton;
    public Text topText;

    public void registration(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("authPanel.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 350, 300));
            stage.setTitle("Аутентификация");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось вернуться к окну регистрации", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topText.setFont(Font.font(null, FontWeight.BOLD, 15));
    }
}
