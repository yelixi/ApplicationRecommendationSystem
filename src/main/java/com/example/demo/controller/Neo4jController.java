package com.example.demo.controller;

import com.example.demo.entity.School;
import com.example.demo.service.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Neo4jController {

    @Autowired
    private Neo4jService neo4jService;

    @RequestMapping(value = "findSchool")
    @ResponseBody
    public School findSchool(@RequestParam("name")String name){
        School s = neo4jService.findSchool(name);
        System.out.println(s);
        return s;
    }
}
