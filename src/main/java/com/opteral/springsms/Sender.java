package com.opteral.springsms;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.smsc.SMSC;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Sender {
    private static final Logger logger = Logger.getLogger(Sender.class);

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
            List<SMS> lista = smsDao.getSMSForSend(aFecha);
            logThis("Hay "+lista.size()+" mensajes para envío");
            processList(lista);

        }
        catch (Exception e) {

            logThis(e.getMessage());
        }


    }

    private void processList(List<SMS> lista)  {

        for (SMS sms : lista) {

            try
            {
                processSMS(sms);

            } catch (Exception e) {

               logThis(e.getMessage());
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


    private void logThis(String mensaje)
    {
        logger.error(mensaje);
    }

}
