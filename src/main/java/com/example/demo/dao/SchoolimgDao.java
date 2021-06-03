package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolimgDao {

    @Select("SELECT `url` FROM `picture` where schoolName=#{schoolName}")
    List<String> selectUrl(@Param("schoolName") String schoolName);
}
