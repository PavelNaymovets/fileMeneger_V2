package com.example.filemeneger_v2.client.network;

import com.example.filemeneger_v2.common.enumsObject.TypeMessage.AuthAsk;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.ListAsk;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Класс NetworkWriter описывает отправку сообщений от клиента на сервер.
 * <p>
 * Реадизована композиционная "has a" связь с классом networkConnection через конструктор класса NetworkWriter.
 * <p>
 * Хранит: единственный экземпляр соединения клиента с сервером.
 *         поток вывода информации.
 */

@Slf4j
public class NetworkWriter {
    private static NetworkWriter networkWriterInstance;
    private ObjectEncoderOutputStream os; //поток вывода для передачи информации на сервер

    private NetworkWriter() {
        NetworkConnection networkConnection = NetworkConnection.getNetworkConnectionInstance(); //получаю соединение с сервером
        os = networkConnection.getObjectEncoderOutputStream(); //получаю поток для отправки сообщений на сервер
    }

    public static NetworkWriter getNetworkWriterInstance() {
        if(networkWriterInstance == null) {
            networkWriterInstance = new NetworkWriter();
        }

        return networkWriterInstance;
    }

    public void sendMessage(Object object) throws IOException {
        os.writeObject(object);
        os.flush();
    }

    public void sendAuthMessage(String login, String password) {
        try {
            sendMessage(new AuthAsk(login, password));
            log.debug("Сообщение от клиента: запрос об авторизации отправлен на сервер пользователем: " + login);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Сообщение от клиента: ошибка при отправке сообщения об авторизации пользователя " + login + " на сервер");
        }
    }

    public void sendListAskMessage(String login, Path path) {
        try {
            sendMessage(new ListAsk(login, path));
            log.debug("Сообщение от клиента: запрос файлового пути до пользовательской папки отправлен на сервер пользователем: " + login);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Сообщение от клиента: ошибка при отправке сообщения об авторизации пользователя " + login + " на сервер");
        }
    }
}
