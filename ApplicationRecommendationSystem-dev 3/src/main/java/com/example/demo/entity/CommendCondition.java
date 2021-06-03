package com.example.demo.entity;

import lombok.Data;

@Data
public class CommendCondition {
    private String score;

    private String region;

    private String schoolLevel;

    private String schoolType;

    private String major;

    private boolean ifMaster;

    private boolean ifDoctor;

    private boolean ifKeyLaboratory;

    private Integer tuition;
}
