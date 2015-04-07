package com.opteral.springsms.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class abstractDao {

    @Autowired
    private JDBCTemplateBean jdbcTemplateBean;


    protected JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplateBean.getJdbcTemplate();
    }
}
