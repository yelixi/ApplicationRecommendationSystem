package com.example.demo.service;

import com.example.demo.entity.CommendCondition;
import com.example.demo.entity.SchoolResult;

public interface CommendService {

    SchoolResult selectByCondition(CommendCondition condition);
}
