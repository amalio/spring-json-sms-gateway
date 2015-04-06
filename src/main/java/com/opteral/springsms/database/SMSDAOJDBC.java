package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component("smsdaojdbc")
public class SMSDAOJDBC implements SMSDAO{

    private static final String SELECT_SMS_BY_ID ="SELECT * FROM sms WHERE id = ?";

    @Autowired
    @Qualifier("jdbctemplate")
    private JdbcTemplate jdbcTemplate;

    public SMSDAOJDBC() {
    }

    public SMSDAOJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(SMS sms) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public SMS getSMS(long id) throws GatewayException {
        try {
            return jdbcTemplate.queryForObject(SELECT_SMS_BY_ID, new RowMappers.SMSRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new GatewayException ("Error: Failed recovering sms");
        }
    }


}
