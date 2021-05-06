package com.example.demo.service.impl;

import com.example.demo.dao.SchoolRepsitory;
import com.example.demo.entity.School;
import com.example.demo.service.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Neo4jServiceImpl implements Neo4jService {
    @Autowired
    SchoolRepsitory schoolRepsitory;
    @Override
    public School findSchool(String name) {
        return schoolRepsitory.findSchool(name);
    }
}
