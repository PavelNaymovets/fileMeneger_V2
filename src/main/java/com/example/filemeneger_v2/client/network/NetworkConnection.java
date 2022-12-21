package com.example.filemeneger_v2.client.network;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * Класс NetworkConnection описывает создание соединения между клиентом и сервером.
 *
 * Хранит: соединение клиента с серверов в единественном экземпляре (применен паттер сингл тон).
 *         поток ввода информации.
 *         поток вывода информации.
 */

@Slf4j
public class NetworkConnection {
    private static NetworkConnection networkConnectionInstance; //соединение с севером
    private Socket socket; //сокет
    private ObjectDecoderInputStream is; //поток ввода информации
    private ObjectEncoderOutputStream os; //поток вывода информации


    private NetworkConnection() {

    }

    public static NetworkConnection getNetworkConnectionInstance() {
        if (networkConnectionInstance == null) {
            networkConnectionInstance = new NetworkConnection();
        }

        return networkConnectionInstance;
    }

    public ObjectEncoderOutputStream getObjectEncoderOutputStream() { //поток вывода для передачи информации на сервер
        return os;
    }

    public ObjectDecoderInputStream getObjectDecoderInputStream() { //поток ввода для чтения информации от сервер
        return is;
    }

    public boolean openConnection(String host, int port) { //открываю соединение с сервером
        if (socket != null && !socket.isClosed()) { //если сокет есть - соединение открыто
            return true;
        }

        try { //октрываю соединение с сервером
            socket = new Socket(host, port);
            log.debug("Сообщение от клиента: создано соединение с хостом: " + host + "\n" + "Порт: " + port);
            os = new ObjectEncoderOutputStream(socket.getOutputStream());
            is = new ObjectDecoderInputStream(socket.getInputStream());

            return true; //соединение установлено
        } catch (IOException e) {
            e.printStackTrace();

            return false; //соединение не установлено
        }
    }

    public void closeConnection() { //закрываю соединение с сервером
        Platform.runLater(() -> {
            log.debug("Сообщение от клиента: закрываю соединение с сервером");

            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.debug("Сообщение от клиента: ошибка при закрытии входного потока донных");
                }
            }

            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.debug("Сообщение от клиента: ошибка при закрытии выходного потока донных");
                }
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.debug("Сообщение от клиента: ошибка при закрытии соединения");
                }
            }
        });
    }
}
