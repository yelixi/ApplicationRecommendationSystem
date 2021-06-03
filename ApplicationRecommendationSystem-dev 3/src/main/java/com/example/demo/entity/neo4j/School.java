package com.example.demo.entity.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


import java.io.Serializable;

@Data
@NodeEntity(label = "School")
public class School implements Serializable {

    @Id
    @GeneratedValue
    private Long identity;

    private String id;

    @Property(name = "school_name")
    private String name;

    @Property(name = "school_biaoshi")
    private String identification;

    @Property(name = "school_code")
    private String code;
    @Property(name = "school_rank")
    private String rank;

}
