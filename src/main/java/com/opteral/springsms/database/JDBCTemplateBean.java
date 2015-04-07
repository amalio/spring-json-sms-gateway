package com.opteral.springsms.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class JDBCTemplateBean {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

}
