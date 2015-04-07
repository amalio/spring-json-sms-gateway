package com.opteral.springsms.smsc;


import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.SMPPSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Profile("!test")

@Component
public class SMSCImp implements SMSC {

    private static final Logger logger = Logger.getLogger(SMSCImp.class);

    @Autowired
    SMPPSessionBean smppSessionBean;

    @Override
    public void sendSMS(SMS sms) throws GatewayException, IOException {

        final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
        registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);


        DataCoding dataCoding = new GeneralDataCoding(Alphabet.ALPHA_DEFAULT);


        byte[] msgText = sms.getText().getBytes("ISO-8859-1");

        try
        {
            String messageId = smppSessionBean.submitShortMessage("CMT", TypeOfNumber.ALPHANUMERIC, NumberingPlanIndicator.UNKNOWN, sms.getSender(), TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, sms.getMsisdn(), new ESMClass(), (byte) 0, (byte) 1, null, null, registeredDelivery, (byte) 0, dataCoding, (byte) 0, msgText);
            sms.setIdSMSC(messageId);
            logger.info("Message submitted, message_id is " + messageId);

        }
        catch (PDUException e)
        {
            logger.error("Invalid PDU parameter", e);
        }
        catch (ResponseTimeoutException e)
        {
            logger.error("Response timeout", e);
        }
        catch (InvalidResponseException e)
        {
            logger.error("Receive invalid respose", e);
        }
        catch (NegativeResponseException e)
        {
            logger.error("Receive negative response", e);
        }
        catch (IOException e)
        {
            logger.error("IO error occur", e);

        }


    }




}
