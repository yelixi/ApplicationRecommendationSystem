package com.example.demo.dao;

import com.example.demo.entity.Cooperation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
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
