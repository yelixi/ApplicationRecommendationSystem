package com.example.demo.controller;


import com.example.demo.entity.School;
import com.example.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;


    @RequestMapping("/getBrief")
    public School getSchoolBriefIntroduction(@RequestParam("name") String name) {
        System.out.println("========");
        return schoolService.findSchoolBrief(name);
    }

}
