package com.example.demo.dao;

import com.example.demo.entity.PapersFocus;

import java.util.List;

public interface PapersFocusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PapersFocus record);

    int insertSelective(PapersFocus record);

    PapersFocus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PapersFocus record);

    int updateByPrimaryKey(PapersFocus record);

    List<PapersFocus> findAll(String openId);
}
