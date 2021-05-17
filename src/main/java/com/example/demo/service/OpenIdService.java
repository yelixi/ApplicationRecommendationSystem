package com.example.demo.service;

import com.example.demo.entity.OpenId;

/**
 * Created by 林夕
 * Date 2021/5/17 0:33
 */
public interface OpenIdService {
    OpenId selectByOpenId(String openId);
}
