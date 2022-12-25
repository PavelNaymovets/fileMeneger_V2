package com.example.filemeneger_v2.server.messageHandler;

import com.example.filemeneger_v2.common.enumsObject.NotificationAlertType.NotificationAlertType;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.*;
import com.example.filemeneger_v2.common.fileInfo.FileInfo;
import com.example.filemeneger_v2.server.dataBase.DataBaseAuthenticateService;
import com.example.filemeneger_v2.server.dataBase.DataBaseConnector;
import com.example.filemeneger_v2.server.listFileHandler.ListFileHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    private DataBaseAuthenticateService authService;

    public MessageHandler() {
        DataBaseConnector dataBaseConnector = DataBaseConnector.getConnectionInstance();
        Connection connection = dataBaseConnector.getConnection();

        this.authService = new DataBaseAuthenticateService(connection);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Клинет подключен...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Клиент отключен...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        TypeMessage type = msg.getTypeMessage();
        switch (type) {
            case AUTH_ASK -> {
                AuthAsk authAsk = (AuthAsk) msg;
                String login = authAsk.getLogin();
                String password = authAsk.getPassword();

                log.debug("Сообщение от сервера: пришло сообщение об авторизации от клиента с логином: " + login);

                String[] userData = authService.getNameAndLastNameByLoginAndPassword(login, password);

                if (userData != null) {
                    log.debug("Сообщение от сервера: удачная авторизация пользователя с логином: " + login);

                    String name = userData[0];
                    String lastName = userData[1];
                    ctx.writeAndFlush(new AuthOk(name, lastName, login));
                } else {
                    ctx.writeAndFlush(new SendError(NotificationAlertType.ERROR, "Ошибка авторизации пользователя", "Неправильный логин или пароль"));
                    log.debug("Сообщение от сервера: неудачная авторизация пользователя с логином: " + login);
                }
            }
            case LIST_ASK -> {
                log.debug("Сообщение от сервера: запрос клиента о списке файлов");
                ListAsk listAsk = (ListAsk) msg;
                String login = listAsk.getLogin();
                Path path = listAsk.getPath();

                if (path == null) {
                    path = ListFileHandler.getStartPath(login);
                }

                List<FileInfo> listFiles = ListFileHandler.getListFiles(path);
                String pathForSend = path.toString(); //Интерфейс Path не сериализуемый. Его не отправить через ctx.

                ctx.writeAndFlush(new ListMessage(listFiles, pathForSend));
            }
        }
    }

//    public void checkerSendError(ChannelHandlerContext ctx, List<FileInfo> listFiles, String pathForSend ) { //Проверка ошибки на отправку сообщения через Netty
//        ChannelFuture cf = ctx.writeAndFlush(new ListMessage(listFiles, pathForSend));
//        if (!cf.isSuccess()) {
//            System.out.println("Send failed: " + cf.cause());
//        }
//    }

}
