package com.example.filemeneger_v2.client.network;

import com.example.filemeneger_v2.client.user.User;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.AbstractMessage;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.AuthOk;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage.AUTH_OK;

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

    //Потоки читающие сообщения от сервера
    private static final int THREAD_COUNT = 2; //количество потоков, которые будут обрабатывать входящие сообщения от сервера.
    private ExecutorService readThreads = Executors.newFixedThreadPool(THREAD_COUNT); //содаю потоки для обработки входящих сообщений от сервера.

    //Соединение с сервером для чтения сообщений
    private ObjectDecoderInputStream is; //поток ввода для чтения информации от сервера

    //Отправка сообщений на сервер
    private NetworkWriter networkWriter; //экземпляр для отправки сообщений на сервер

    //Информация о пользователе
    private User user;

    public NetworkReader(NetworkWriter networkWriter) {
        NetworkConnection networkConnection = NetworkConnection.getNetworkConnectionInstance(); //получаю соединение с сервером
        this.is = networkConnection.getObjectDecoderInputStream(); //получаю поток ввода для чтения сообщений
        this.networkWriter = networkWriter; //присваиваю объект для отправки сообщений на сервер
    }

    public void readMessageFromServer() {//обработка входящих сообщений
        log.debug("Клиент ожидает сообщения от сервера...");
        readThreads.execute(() -> {
            while (true) {
                try {
                    AbstractMessage message = (AbstractMessage) is.readObject();
                    if (message.getTypeMessage() == AUTH_OK) {
                        log.debug("Сообщение от сервера: пользователь успешно авторизован");
                        AuthOk authOk = (AuthOk) message;
                        user = new User(authOk.getUserName(), authOk.getLogin());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    log.error("Сообщение от клиента: Ошибка при отправке сообщения клиентом на сервер");
                }
            }
        });
    }
}
