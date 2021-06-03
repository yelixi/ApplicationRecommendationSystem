package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.SocketConstant;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author whz
 * @date 2021/3/30 下午8:23
 **/
public class SocketClient {


    /**
     * socket通信
     *
     * @param method 调用接口名（在constant里指定）
     * @param param  传参(json格式)
     * @return java.lang.Object
     */
    public static Object socketHandle(Integer method, Object param) {
        StringBuilder sb = null;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", method);
        paramMap.put("param", param);
//        if(param!=null) {
//            paramMap = JSONObject.parseObject(JSONObject.toJSONString(param));
//        }
        try (Socket socket =
                     new Socket(SocketConstant.SERVER_HOST, SocketConstant.SERVER_PORT)) {

            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);

            String jsonParam = JSONObject.toJSONString(paramMap);
            System.out.println(jsonParam);
            out.print(jsonParam);
            out.print(SocketConstant.INTERRUPT_FLAG);

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String tmp;
            sb = new StringBuilder();
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp).append('\n');
            }
            return JSONObject.toJSON(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
            assert sb != null;
            return JSONObject.toJSON(sb.toString());
        } finally {
            System.out.println("远程接口调用结束.");
        }
    }
}
