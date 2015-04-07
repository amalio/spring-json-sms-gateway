package com.opteral.springsms;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.smsc.SMSC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Sender {

    private SmsDao smsDao;
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
        catch (Exception e) {

            //TODO log this
        }


    }

    private void processList(List<SMS> lista)  {

        for (SMS sms : lista) {

            try
            {
                processSMS(sms);

            } catch (Exception e) {

               //TODO log this
            }

        }
    }

    private void processSMS(SMS sms) throws GatewayException, IOException {

        smsc.sendSMS(sms);

        if (sms.getIdSMSC() != null && !sms.getIdSMSC().isEmpty()) {

            sms.setSms_status(SMS.SMS_Status.ONSMSC);
            smsDao.update(sms);
        }
    }




}
