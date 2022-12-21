package com.example.filemeneger_v2.common.filesWork;

import com.example.filemeneger_v2.common.enumsObject.TypeMessage.FileMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
public class FileSender {
    private static final int BUF_SIZE = 10000; //Размер буфера чтения данных

    //Отправка файла частями. Клиент -> Сервер. Сервер -> Клиент.
    public void sendFileByPieces(String filePath, String login, String dirDestination, ObjectEncoderOutputStream os, ChannelHandlerContext ctx) {
        try {
            Path fileForSend = Paths.get(filePath);
            float lengthFileForSend = Files.size(fileForSend);
            int countPackage = (int) Math.ceil(lengthFileForSend / BUF_SIZE);
            int numberPackage = 1; //порядковый номер пакета с байтовыми данными

            log.debug("Отправка файла: " + fileForSend.getFileName() + " ...");
            log.debug("Размер файла: " + lengthFileForSend + " байт");
            log.debug("Количество пакетов для отправки: " + countPackage);

            //Разбивка файла на части
            try (FileInputStream fis = new FileInputStream(fileForSend.toString())) {
                byte[] buf = new byte[BUF_SIZE];
                int byteRead;
                while ((byteRead = fis.read(buf)) != -1) {
                    if (byteRead < buf.length) { //Если количество прочитанных байт меньше размера буфера
                        buf = Arrays.copyOf(buf, byteRead); //Перезаписываем данные в буфере. Копируем количество считанных данных в буфер. Избавляемся от лишних байтов.
                    }

                    //Отправка файла частями BUF_SIZE
                    if (os != null) { //Отправка файла Клиент -> Сервер
                        os.writeObject(new FileMessage(login, fileForSend.getFileName().toString(), null, buf, numberPackage, countPackage));
                        log.debug("На сервер отправлен файл: " + numberPackage + " из " + countPackage);
                    } else { //Отправка файла Сервер -> Клиент
                        ctx.writeAndFlush(new FileMessage(login, fileForSend.getFileName().toString(), dirDestination, buf, numberPackage, countPackage));
                        log.debug("На клиент отправлен файл: " + numberPackage + " из " + countPackage);
                    }
                    numberPackage++;
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка отправки файла", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Файл не найден", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
