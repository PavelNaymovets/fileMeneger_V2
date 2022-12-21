package com.example.filemeneger_v2.client;

import com.example.filemeneger_v2.common.directorysWork.DirectoryCreator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FileManagerPanelController implements Initializable {

    @FXML
    public VBox leftPanel;

    @FXML
    public VBox rightPanel;

    private PanelController leftPC;
    private PanelController rightPC;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void copyBtnAction() {
        if(leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null;
        PanelController dstPC = null;

        if(leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if(rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось скопировать указанный файл", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void btnBack(ActionEvent actionEvent) {
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

    public void createDirectory(ActionEvent actionEvent) {
        Path source = Paths.get(Paths.get("").toAbsolutePath().toString(),"11", "Тестовая директория");
        DirectoryCreator filesWork = new DirectoryCreator();
        filesWork.createAnyDirectory(source);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PanelController leftPanelController = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPanelController = (PanelController) rightPanel.getProperties().get("ctrl");

        this.leftPC = leftPanelController;
        this.rightPC = rightPanelController;

//        AuthPanelController controller = AuthPanelController.getAuthPanelController(); //пример получения логина из окна аутентификации
//        String login = controller.getLogin();

        Path of = Paths.get(""); //путь к корневой папке проекта
        Path pathLeftPC = Paths.get(of.toAbsolutePath().toString(), "cloud-storage-client");
        Path pathRightPC = Paths.get(of.toAbsolutePath().toString(), "cloud-storage-server");

        leftPC.updateList(pathLeftPC);
        rightPC.updateList(pathRightPC);
    }
}