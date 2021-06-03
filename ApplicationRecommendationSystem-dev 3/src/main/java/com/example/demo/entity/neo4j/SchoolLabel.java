package com.example.demo.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;

@Data
@Builder
@NodeEntity(label = "SchoolLabel")
public class SchoolLabel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "school_label_1")
    private String schoolLabel1;

    @Property(name = "school_label_2")
    private String schoolLabel2;

    @Property(name = "school_label_3")
    private String schoolLabel3;

}
