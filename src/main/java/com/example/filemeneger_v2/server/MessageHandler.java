package com.example.filemeneger_v2.server;

import com.example.filemeneger_v2.common.enumsObject.TypeMessage.AbstractMessage;
import com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage.AUTH_ASK;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {

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
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage absMsg) throws Exception {
        TypeMessage type = absMsg.getTypeMessage();
        if(type == AUTH_ASK) {
            log.debug("Пришло сообщение от клиента");
            ctx.writeAndFlush("Привет от нетти сервера!");
        }
    }
}
