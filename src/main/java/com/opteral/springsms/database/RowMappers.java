package com.opteral.springsms.database;

import com.opteral.springsms.model.SMS;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMappers {
    public  static final class SMSRowMapper implements RowMapper<SMS> {
        public SMS mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            SMS sms = new SMS();

            sms.setId(resultSet.getLong("id"));
            sms.setUser_id(resultSet.getInt("user_id"));
            sms.setMsisdn(resultSet.getString("msisdn"));
            sms.setSender(resultSet.getString("sender"));
            sms.setText(resultSet.getString("text"));
            sms.setDatetimeScheduled(resultSet.getTimestamp("datetime_scheduled"));
            sms.setTest(false);
            sms.setAckurl(resultSet.getString("ackurl"));
            sms.setSubid(resultSet.getString("subid"));
            sms.setSms_status(SMS.SMS_Status.fromInt(resultSet.getInt("status")));

            return sms;
        }
    }
}
