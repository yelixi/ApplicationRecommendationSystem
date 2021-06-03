package com.example.demo.dao;

import com.example.demo.entity.CollegesFocus;

import java.util.List;

public interface CollegesFocusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollegesFocus record);

    int insertSelective(CollegesFocus record);

    CollegesFocus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollegesFocus record);

    int updateByPrimaryKey(CollegesFocus record);

    List<CollegesFocus> findAll(String openId);
}
