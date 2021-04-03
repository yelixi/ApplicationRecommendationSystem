package com.example.demo.entity;

import lombok.Data;

@Data
public class SchoolResult {
    private String schoolId;

    private String schoolLevel;

    private String schoolName;

    private String lowestScore;

    private String position;

    private String region;

    private String introduction;
}