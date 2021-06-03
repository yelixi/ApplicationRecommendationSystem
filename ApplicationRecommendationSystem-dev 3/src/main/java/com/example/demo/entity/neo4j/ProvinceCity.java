package com.example.demo.entity.neo4j;

import lombok.Data;

import java.util.List;

/**
 * @author 11834
 */
@Data
public class ProvinceCity {
    private String province;
    private List<String> city;
}
