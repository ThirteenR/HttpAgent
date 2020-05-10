package com.thirteen.java;

import java.io.*;
import java.net.Socket;

/**
 * Author: thirteen
 * date-time: 2020-04-23 18:43
 **/
public class THttpClient {

    public String execute(Configuration config, String request) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            Socket socket = new Socket(config.getAgentHost(), config.getAgentPort());

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            request = closeKeepLive(request);
            outputStream.write(request.getBytes());
            outputStream.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line+"\r\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    private String closeKeepLive(String request){
        request = request.replaceFirst("Connection: Keep-Alive","Connection: close");
        request = request.replaceFirst("Connection: keep-alive","Connection: close");
        return request;
    }
}
