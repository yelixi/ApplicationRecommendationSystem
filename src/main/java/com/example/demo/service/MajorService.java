package com.example.demo.service;

import com.example.demo.entity.Major;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:14
 */
public interface MajorService {

    Map<String,String> getMajorCategory(String majorName);

    Major getMajorCard(String name);
}
