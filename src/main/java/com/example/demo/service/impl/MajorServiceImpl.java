package com.example.demo.service.impl;

import com.example.demo.entity.Major;
//import com.example.demo.repository.MajorRepository;
import com.example.demo.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:14
 */
@Service
public class MajorServiceImpl implements MajorService {

    /*@Resource
    private MajorRepository majorRepository;*/

    @Override
    public Map<String, String> getMajorCategory(String majorName) {
        //TODO
        return null;
    }

    @Override
    public Major getMajorCard(String name) {
        //TODO
        return null;
    }
}
