package com.thirteen.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Author: thirteen
 * date-time: 2020-04-23 18:42
 **/
public class THttpServer implements Runnable {
    private Socket socket;
    private Configuration config;
    private String request;
    private HttpHead httpHead;

    public THttpServer(Socket socket, Configuration config) {
        this.socket = socket;
        this.config = config;
    }

    public void readRequest() {
        InputStream inputStream = null;
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        StringBuffer sb = new StringBuffer();
        String line = "";
        try {
            inputStream = socket.getInputStream();
            while (true) {
                byte read = (byte) inputStream.read();

                if (read == -1) break;
                bytes.add(read);
                // 读完一行
                if (read == 10) {
                    byte[] bytes1 = new byte[bytes.size()];
                    for (int i = 0; i < bytes.size(); i++) {
                        bytes1[i] = bytes.get(i);
                    }
                    bytes.clear();
                    line = new String(bytes1);
                    // 将请求数据追加
                    sb.append(line);
                    // 读到空行,请求头读取完毕
                    if (line.equals("\r\n")) {
                        httpHead = new HttpHead(sb.toString());
                        // POST请求读取报文体
                        if (httpHead.getMethod().equals("POST")) {
                            byte[] bytes2 = new byte[httpHead.getContentLength()];
                            inputStream.read(bytes2);
                            sb.append(new String(bytes2));
                        }
                        break;
                    }
                }

            }
        } catch (IOException e) {
            bytes.clear();
            e.printStackTrace();
        }finally {

//            try {
//                if (inputStream!=null){
//                inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        request = sb.toString();
        System.out.println("request:\n" + request);
    }

    public void response(String response) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    if(!socket.isClosed())
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String agentClient() {
        THttpClient tHttpClient = new THttpClient();
        String execute = tHttpClient.execute(config, request);
        return execute;
    }

    @Override
    public void run() {
        readRequest();
        String s = agentClient();
        System.out.println("response:\n" + s);
        response(s);
    }
}
