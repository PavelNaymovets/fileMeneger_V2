package com.example.filemeneger_v2.server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
