package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.util.SocketClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author whz
 * @date 2021/3/30 下午8:48
 **/
@RestController
public class TestController {
    @GetMapping("/socket")
    public String socketTest(){
        User user=new User();
        user.setUsername("test");
        user.setPhone("12355");
        //Object obj=SocketClient.socketHandle("getAll", null);
        //return JSONObject.toJSONString(obj);
        return null;
    }
}
