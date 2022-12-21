package com.example.filemeneger_v2.client.network;

import com.example.filemeneger_v2.common.enumsObject.TypeMessage.AuthAsk;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
    private ObjectEncoderOutputStream os; //поток вывода для передачи информации на сервер

    public NetworkWriter() {
        NetworkConnection networkConnection = NetworkConnection.getNetworkConnectionInstance(); //получаю соединение с сервером
        this.os = networkConnection.getObjectEncoderOutputStream(); //получаю поток для отправки сообщений на сервер
    }

    public void sendMessage(Object object) throws IOException {
        os.writeObject(object);
        os.flush();
    }

    public void sendAuthMessage(String login, String password) {
        try {
            sendMessage(new AuthAsk(login, password));
            log.debug("Сообщение отправлено на сервер");
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Сообщение от клиента: ошибка при отправке сообщения об авторизации пользователя " + login + " на сервер");
        }
    }
}
