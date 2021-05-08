package com.example.demo.service.impl;

import com.example.demo.entity.School;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;


    @Override
    public School findSchoolBrief(String name) {
        return schoolRepository.findSchool(name);
    }
}
