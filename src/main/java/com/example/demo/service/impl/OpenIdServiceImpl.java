package com.example.demo.service.impl;

import com.example.demo.dao.OpenIdMapper;
import com.example.demo.entity.OpenId;
import com.example.demo.service.OpenIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/5/17 0:34
 */
@Service
public class OpenIdServiceImpl implements OpenIdService {

    @Resource
    private OpenIdMapper openIdMapper;

    @Override
    public OpenId selectByOpenId(String openId) {
        return openIdMapper.selectByOpenId(openId);
    }
}
