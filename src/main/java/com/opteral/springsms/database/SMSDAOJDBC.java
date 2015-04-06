package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class SMSDAOJDBC implements SMSDAO{
    @Override
    public void persist(SMS sms) throws GatewayException {
        throw new NotImplementedException();
    }
}
