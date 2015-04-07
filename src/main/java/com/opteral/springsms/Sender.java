package com.opteral.springsms;

import com.opteral.springsms.database.SMSDAO;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.smsc.SMSC;

import java.io.IOException;
import java.util.List;

public class Sender {

    private SMSDAO smsdao;
    private SMSC smsc;

    public Sender(SMSDAO smsdao, SMSC smsc) {

        this.smsdao = smsdao;
        this.smsc = smsc;
    }

    public void send(java.sql.Date aFecha)  {

        try {

            processList(smsdao.getSMSForSend(aFecha));

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
            smsdao.update(sms);
        }
    }




}
