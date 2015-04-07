package com.opteral.springsms.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class abstractDao {
    @Autowired
    @Qualifier("jdbctemplate")
    protected JdbcTemplate jdbcTemplate;

}
