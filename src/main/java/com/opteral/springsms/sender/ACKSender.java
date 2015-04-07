package com.opteral.springsms.sender;

import com.opteral.springsms.Utilities;
import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//TODO Makeme a bean
public class ACKSender {

    private static final Logger logger = Logger.getLogger(ACKSender.class);

    public static void sendACK(ACK ack, SmsDao smsDao) throws GatewayException {

        SMS sms = smsDao.getSMS(ack.getIdSMS());

        if (sms.getAckurl().isEmpty())
            return;


        String url = null;
        try {
            url = sms.getAckurl()+"?msisdn="+sms.getMsisdn()+"&status="+sms.getSms_status().getValue()+"&subid="+sms.getSubid()+"&datetime="+ URLEncoder.encode(sms.getDatetimeLastModified().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

            logger.error("Error: Failed coding URL on ACK"+e.getMessage());
            throw new GatewayException("Error: Failed coding URL on ACK"+e.getMessage());

        }

        Utilities.sendGet(url);
        logger.info("ACK sended for sms: "+ack.getIdSMS());

    }
}
