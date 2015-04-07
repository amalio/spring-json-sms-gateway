package com.opteral.springsms.smsc;



import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;

import java.io.IOException;


public interface SMSC {
    public String sendSMS(SMS sms) throws GatewayException, IOException;
}
