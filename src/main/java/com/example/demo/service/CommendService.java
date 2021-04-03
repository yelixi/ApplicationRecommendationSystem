package com.example.demo.service;

import com.example.demo.entity.CommendCondition;
import com.example.demo.entity.SchoolResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CommendService {

    List<SchoolResult> selectByCondition(CommendCondition condition) throws JsonProcessingException;
}
