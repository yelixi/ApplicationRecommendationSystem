package com.example.demo.entity;

import lombok.Data;

import java.util.List;


@Data
public class SearchCondition {

    private String schoolName;

    private String schoolLevel;

    private String lowestScore;

    private String position;

    private List<String> region;

    private List<String> major;

    private boolean ifMaster;

    private boolean ifDoctor;

    private boolean ifKeyLaboratory;





}
