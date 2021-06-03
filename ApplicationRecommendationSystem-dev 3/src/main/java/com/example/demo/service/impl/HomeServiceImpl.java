package com.example.demo.service.impl;

import com.example.demo.entity.College;
import com.example.demo.entity.Profession;
import com.example.demo.service.HomeService;
import org.springframework.stereotype.Service;

/**
 * Created by 林夕
 * Date 2021/5/20 0:35
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public Object find(String type, String search) {
        if(type.equals("profession")){
            Profession profession = new Profession();
            profession.setName(search);
            profession.setMajorIntroduction("专业的具体介绍");
            profession.setId(1);
            return profession;
        }
        else{
            College college = new College();
            college.setName(search);
            college.setCompetentDepartment("教育局");
            college.setMinimumRank(10000);
            college.setMinimumScore(600);
            college.setProvince("江苏");
            college.setSchoolCord("10001");
            college.setUneven("双一流");
            college.setId(1);
            return college;
        }
    }
}
