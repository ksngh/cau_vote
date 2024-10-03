package caugarde.vote.common;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;

@Component
public class DataSQLInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;

    public DataSQLInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        // 더미 데이터 삽입 체크
        String checkIfExistsSql = "SELECT COUNT(*) FROM AUTHORITY";
        Integer count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class);

        if (count != null && count == 0) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(new ClassPathResource("data.sql"));
            resourceDatabasePopulator.execute(dataSource);
        }
    }

}