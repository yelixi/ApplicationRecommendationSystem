package com.example.demo.dao;

import com.example.demo.entity.School;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepsitory extends CrudRepository<School,Long> {
    @Query(value = "MATCH(s:school) WHERE s.name=$name return s")
    School findSchool(@Param("name")String name);


}
