package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;

public interface SMSDAO {
    void insert(SMS sms) throws GatewayException;
    void update(SMS sms) throws GatewayException;
    public SMS getSMS(long id) throws GatewayException;
}
