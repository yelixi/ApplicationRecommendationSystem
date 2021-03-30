package com.example.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @date 2021/3/30 下午8:25
 **/
@Component
public class SocketConstant {
    public static String SERVER_HOST;
    public static Integer SERVER_PORT;
    public static final String INTERRUPT_FLAG = "over";


    @Value("${socket.host}")
    public void setServerHost(String host) {
        SERVER_HOST = host;
    }

    @Value("${socket.port}")
    public void setServerPort(Integer port) {
        SERVER_PORT = port;
    }


}
