package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan("com.example.demo.dao")
@EnableNeo4jRepositories
public class ArsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArsApplication.class, args);
    }

}
