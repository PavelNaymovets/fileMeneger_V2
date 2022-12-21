package com.example.filemeneger_v2.common.directorysWork;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
public class DirectoryCreator {
    //Создание стартовой директории
    public void createStartDirectory(Path dirPath) {
        try {
            if (!Files.exists(dirPath)) {
                log.debug("Директории " + dirPath.getFileName().toString() + " не существует");
                Files.createDirectory(dirPath);
                log.debug("Директория " + dirPath.getFileName().toString() + " успешно создана");
            }
        } catch (Exception e) {
            log.debug("Директория уже существует");
        }
    }

    //Создание любой директории внутри сервера или клиента
    public void createAnyDirectory(Path dirPath) {
        try {
            if (!Files.exists(dirPath)) {
                log.debug("Директории " + dirPath.getFileName().toString() + " не существует");
                Files.createDirectory(dirPath);
                log.debug("Директория " + dirPath.getFileName().toString() + " успешно создана");
            } else {
                Path copyDirPath = Paths.get(dirPath + "_копия");
                if (!Files.exists(copyDirPath)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Директория уже существует. Продолжить создание?");
                    ButtonType cancelButton = new ButtonType("ОТМЕНА", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getDialogPane().getButtonTypes().addAll(cancelButton);
                    Optional<ButtonType> pressedButton = alert.showAndWait();
                    if (pressedButton.isPresent() && pressedButton.get() == ButtonType.OK) {
                        Files.createDirectory(copyDirPath);
                        log.debug("Директория успешно создана");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Копия папки уже существует");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            log.debug("Ошибка в создании директории");
        }
    }
}
