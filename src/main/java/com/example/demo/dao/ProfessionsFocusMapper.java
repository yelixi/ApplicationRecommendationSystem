package com.example.demo.dao;

import com.example.demo.entity.ProfessionsFocus;

import java.util.List;

public interface ProfessionsFocusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProfessionsFocus record);

    int insertSelective(ProfessionsFocus record);

    ProfessionsFocus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProfessionsFocus record);

    int updateByPrimaryKey(ProfessionsFocus record);

    List<ProfessionsFocus> findAll(String openId);
}
