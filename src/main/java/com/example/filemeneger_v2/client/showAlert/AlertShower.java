package com.example.filemeneger_v2.client.showAlert;

import com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import static com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType.ERROR;
import static com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType.INFORMATION;

/**
 * Класс AlertShower показывает уведомления пользователю в отдельном окне.
 */

public class AlertShower {
    private static Alert alert; //всплывающее уведомление

    public static void showAlert(NotificationAlertType notificationType, String errorType, String messageError) {
        Platform.runLater(() -> {
            if (notificationType == ERROR) { //тип: ошибка
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка"); //заголовок всплывающего окна
            } else if (notificationType == INFORMATION) { //тип: подтверждение
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Подтверждение"); //заголовок всплывающего окна
            }
            alert.setHeaderText(errorType); //тип ошибки. Например: ошибка подключения к серверу
            alert.setContentText(messageError); //описание ошибки

            alert.showAndWait(); //показываю сообщение
        });
    }
}
