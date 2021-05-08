package com.example.demo.service;

import com.example.demo.entity.School;

public interface SchoolService {

    /**
     * 根据学校名称，获取学校信息
     * @param name
     * @return
     */
    School findSchoolBrief(String name);
}
