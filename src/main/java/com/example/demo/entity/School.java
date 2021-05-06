package com.example.demo.entity;





import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "school")
public class School implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String biaoshima;

    private String school_code;

    private String introduction;

    public School(){

    }
    public School(String name, String biaoshima, String school_code, String introduction){
        this.name = name;
        this.biaoshima=biaoshima;
        this.school_code =school_code;
        this.introduction=introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiaoshima() {
        return biaoshima;
    }

    public void setBiaoshima(String biaoshima) {
        this.biaoshima = biaoshima;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
