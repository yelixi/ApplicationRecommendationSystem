package com.example.demo.service;


import com.example.demo.entity.School;
import org.springframework.stereotype.Service;

/**
 * @ClassName Neo4jService
 * @Description TODO
 * @Author
 **/

public interface Neo4jService {



    School findSchool(String name);
}