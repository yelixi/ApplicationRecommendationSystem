package com.example.demo.dao;

import com.example.demo.entity.Cooperation;

public interface CooperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cooperation record);

    int insertSelective(Cooperation record);

    Cooperation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cooperation record);

    int updateByPrimaryKey(Cooperation record);

    Cooperation selectByOpenIdAndComName(String openId,String comName);

    Cooperation selectByOpenId(String openId);
}
