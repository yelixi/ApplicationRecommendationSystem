package com.example.demo.repository;

import com.example.demo.entity.neo4j.Major;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11834
 */
@Repository
public interface AreaRepository extends Neo4jRepository<String, Long> {
    /**
     * 获取所有省份
     * @return
     */
    @Query("MATCH (n:Province) RETURN n.province_name")
    List<String> getProvinces();


    /**
     * 获取省份下所有城市
     * @param province
     * @return
     */
    @Query("MATCH (n:Province{province_name:$province})-[r:INCLUDE]->(CT) RETURN CT.city_name")
    List<String> getCitiesByProvince(String province);
}

