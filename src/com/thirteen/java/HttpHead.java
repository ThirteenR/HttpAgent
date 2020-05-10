package com.thirteen.java;

/**
 * Author: thirteen
 * date-time: 2020-04-23 19:56
 **/
public class HttpHead {
    private String contentType;
    private int contentLength;
    private String method;
    private String uti;
    public HttpHead(String request){
        parse(request);
    }
    public void parse(String request){
        if(request.startsWith("POST")){
            this.method = "POST";
        }else if(request.startsWith("GET")){
            this.method = "GET";
        }
        String[] split = request.split("\rrn");
        for (String s:split) {
            if(s.startsWith("Content-Length:")){
               this.contentLength =Integer.parseInt(s.split(":")[1]);
            }
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUti() {
        return uti;
    }

    public void setUti(String uti) {
        this.uti = uti;
    }
}
