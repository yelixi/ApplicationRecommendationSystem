package com.example.demo.dao;

import com.example.demo.entity.OpenId;

public interface OpenIdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpenId record);

    int insertSelective(OpenId record);

    OpenId selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpenId record);

    int updateByPrimaryKey(OpenId record);

    OpenId selectByOpenId(String openId);
}
