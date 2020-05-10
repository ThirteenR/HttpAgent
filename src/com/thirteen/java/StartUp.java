package com.thirteen.java;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: thirteen
 * date-time: 2020-04-23 20:21
 **/
public class StartUp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8090);
        System.out.println("Server Active");
        ExecutorService tp = Executors.newFixedThreadPool(20);
        Configuration configuration = new Configuration();
        while (true){
            Socket accept = serverSocket.accept();
            tp.execute(new THttpServer(accept,configuration));
        }


    }
}
