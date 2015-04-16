package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;

import java.sql.Date;
import java.util.List;

public interface SmsDao {
    void insert(SMS sms) throws GatewayException;
    void update(SMS sms) throws GatewayException;
    void delete(SMS sms);
    SMS getSMS(long id);
    void updateSMS_Status(ACK ack) throws GatewayException;
    List<SMS> getSMSForSend(Date aFecha) throws GatewayException;
    SMS getSMSfromIdSMSC(String idSMSC) throws GatewayException;
}
