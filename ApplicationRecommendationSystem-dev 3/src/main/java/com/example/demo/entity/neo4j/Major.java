package com.example.demo.entity.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by 林夕
 * Date 2021/5/10 19:21
 */
@Data
@NodeEntity(label = "Major")
public class Major {
    @Id
    @GeneratedValue
    private Long identity;

    private String id;
    @Property(name = "school_major")
    private String majorName;
}
