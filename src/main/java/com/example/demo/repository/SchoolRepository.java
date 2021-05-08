package com.example.demo.repository;

import com.example.demo.entity.School;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableReactiveNeo4jRepositories
public interface SchoolRepository extends Neo4jRepository<School, Long> {

    @Query("MATCH(s:school) WHERE s.name=$name return s")
    School findSchool(@Param("name") String name);


}
