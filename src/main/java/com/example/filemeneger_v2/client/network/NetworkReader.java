package com.example.filemeneger_v2.client.network;

import com.example.filemeneger_v2.client.pathHandler.PathKeeper;
import com.example.filemeneger_v2.client.showAlert.AlertShower;
import com.example.filemeneger_v2.client.user.User;
import com.example.filemeneger_v2.common.directorysWork.DirectoryCreator;
import com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.*;
import com.example.filemeneger_v2.common.fileInfo.FileInfo;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage.LIST_MESSAGE;


/**
 * Класс NetworkReader описывает чтение сообщений клиентом от сервера.
 * <p>
 * Реализована композиционная "has a" связь с классом networkConnection через конструктор класса NetworkReader.
 * Пока не создано соединение с сервером прочитать сообщения от сервера невозможно.
 * <p>
 * Хранит: единственный экземпляр соединения клиента с сервером.
 */

@Slf4j
public class NetworkReader {
    private static NetworkReader networkReaderInstance;

    //Потоки читающие сообщения от сервера
    private static final int THREAD_COUNT = 2; //количество потоков, которые будут обрабатывать входящие сообщения от сервера.
    private ExecutorService readThreads = Executors.newFixedThreadPool(THREAD_COUNT); //содаю потоки для обработки входящих сообщений от сервера.

    //Соединение с сервером для чтения сообщений
    private ObjectDecoderInputStream is; //поток ввода для чтения информации от сервера

    //Отправка сообщений на сервер
    private NetworkWriter networkWriter; //экземпляр для отправки сообщений на сервер

    //Информация о пользователе
    private User user;

    private NetworkReader() {
        NetworkConnection networkConnection = NetworkConnection.getNetworkConnectionInstance(); //получаю соединение с сервером
        is = networkConnection.getObjectDecoderInputStream(); //получаю поток ввода для чтения сообщений
        networkWriter = NetworkWriter.getNetworkWriterInstance();
    }

    public static NetworkReader getNetworkReaderInstance() {
        if(networkReaderInstance == null) {
            networkReaderInstance = new NetworkReader();
        }

        return networkReaderInstance;
    }

    public void readMessageFromServer() {//обработка входящих сообщений
        log.debug("Клиент ожидает сообщения от сервера...");
        readThreads.execute(() -> {
            while (true) {
                try {
                    AbstractMessage message = (AbstractMessage) is.readObject();
                    switch (message.getTypeMessage()) {
                        case AUTH_OK -> {
                            log.debug("Сообщение от сервера: пользователь успешно авторизован");
                            AuthOk authOk = (AuthOk) message;

                            //Данные о пользователе
                            String name = authOk.getName();
                            String lastName = authOk.getLastName();
                            String login = authOk.getLogin();
                            user = new User(name, lastName, login);

                            //Стартовая директория пользователя на стороне клиента
                            Path path = Paths.get(Paths.get("").toAbsolutePath().toString(), "cloud-storage-client", login);
                            DirectoryCreator.createStartDirectory(path);

                            //Стартовая директория пользователя на стороне сервера
                            PathKeeper.setPath(null);
                            Path startPath = PathKeeper.getPath();

                            //Запрос расположения стартовой директории пользователя на сервере и файлов в ней
                            networkWriter.sendListAskMessage(login, startPath);
                        }
                        case SEND_ERROR -> {
                            SendError sendError = (SendError) message;

                            NotificationAlertType notificationAlertType = sendError.getNotificationAlertType();
                            String errorType = sendError.getErrorType();
                            String messageError = sendError.getMessageError();

                            AlertShower.showAlert(notificationAlertType, errorType, messageError);
                        }
                        case LIST_MESSAGE -> {
                            log.debug("Сообщение от клиента: пришли пути от сервера");
                            ListMessage listMessage = (ListMessage) message;

                            Path path = Paths.get(listMessage.getPath());
                            List<FileInfo> listFiles = listMessage.getListFiles();

                            PathKeeper.setPath(path);
                            PathKeeper.setListFiles(listFiles);

                            //Метод открывающий окна приложения
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    log.error("Сообщение от клиента: Ошибка при отправке сообщения клиентом на сервер");
                }
            }
        });
    }
}
