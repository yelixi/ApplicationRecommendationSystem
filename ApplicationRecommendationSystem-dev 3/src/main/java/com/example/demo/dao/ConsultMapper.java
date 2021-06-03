package com.example.demo.dao;

import com.example.demo.entity.Consult;

import java.util.List;

public interface ConsultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Consult record);

    int insertSelective(Consult record);

    Consult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consult record);

    int updateByPrimaryKey(Consult record);

    List<Consult> findAll();
}
