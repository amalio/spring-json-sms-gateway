package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Date;
import java.util.List;

@Component
public class SmsDaoHibernate extends AbstractHibernateDao implements SmsDao {
    @Override
    public void insert(SMS sms) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public void update(SMS sms) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public void delete(SMS sms) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public SMS getSMS(long id) throws GatewayException {
        return (SMS) currentSession().get(SMS.class, id);
    }

    @Override
    public void updateSMS_Status(ACK ack) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public List<SMS> getSMSForSend(Date aFecha) throws GatewayException {
        throw new NotImplementedException();
    }

    @Override
    public SMS getSMSfromIdSMSC(String idSMSC) throws GatewayException {
        throw new NotImplementedException();
    }
}
