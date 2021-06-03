package com.example.demo.conf;


import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName Neo4jConfig
 * @Description 当操作neo4j图数据库数据需映射到实体类时 该配置类内容可注释
 * 该配置类主要获取 Session
 * @Author
 **/
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "com.example.demo.repository")
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String url;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean(name = "session")
    public Session neo4jSession() {
        Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        return driver.session();
    }


}