package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public void insert(SMS sms) throws GatewayException {

        Timestamp timestampNow = new Timestamp(new Date().getTime());

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("sms");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("user_id", sms.getUser_id());
        args.put("subid", sms.getSubid());
        args.put("msisdn", sms.getMsisdn());
        args.put("sender", sms.getSender());
        args.put("text", sms.getText());
        args.put("status", sms.getSms_status().getValue());
        args.put("ackurl", sms.getAckurl());
        args.put("datetime_inbound", timestampNow);
        args.put("datetime_lastmodified", timestampNow);
        if (sms.getDatetimeScheduled() != null)
            args.put("datetime_scheduled", new Timestamp(sms.getDatetimeScheduled().getTime()));
        else
            args.put("datetime_scheduled", null);
        long id = jdbcInsert.executeAndReturnKey(args).longValue();
        sms.setId(id);
    }

    @Override
    public void update(SMS sms) throws GatewayException {
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
