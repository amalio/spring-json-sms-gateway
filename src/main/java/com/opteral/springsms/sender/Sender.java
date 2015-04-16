package com.opteral.springsms.sender;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.smsc.SMSC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Profile("!test")
@Component
public class Sender {

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private SMSC smsc;

    public Sender() {
    }

    public Sender(SmsDao smsDao, SMSC smsc) {

        this.smsDao = smsDao;
        this.smsc = smsc;
    }

    public void send(java.sql.Date aFecha)  {

        try {
            processList(smsDao.getSMSForSend(aFecha));

        }
        catch (Exception ignored) {
        }
    }

    private void processList(List<SMS> lista)  {

        for (SMS sms : lista) {

            try
            {
                processSMS(sms);

            } catch (Exception ignored) {
            }

        }
    }

    private void processSMS(SMS sms) throws GatewayException, IOException {

        smsc.sendSMS(sms);

        if (isThereAreValidIdSMSC(sms)) {
            smsDao.update(sms);
        }
    }

    private boolean isThereAreValidIdSMSC(SMS sms){
        return sms.getIdSMSC() != null && !sms.getIdSMSC().isEmpty();
    }



}
