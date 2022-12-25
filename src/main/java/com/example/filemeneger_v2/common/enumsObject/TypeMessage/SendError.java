package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

import com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType;

public class SendError implements AbstractMessage {
    private NotificationAlertType notificationAlertType;
    private String errorType;
    private String messageError;

    public SendError(NotificationAlertType notificationAlertType, String errorType, String messageError) {
        this.notificationAlertType = notificationAlertType;
        this.errorType = errorType;
        this.messageError = messageError;
    }

    public NotificationAlertType getNotificationAlertType() {
        return notificationAlertType;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessageError() {
        return messageError;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.SEND_ERROR;
    }
}
