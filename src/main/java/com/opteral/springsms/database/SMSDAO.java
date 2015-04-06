package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;

public interface SMSDAO {
    void persist(SMS sms) throws GatewayException;
}
