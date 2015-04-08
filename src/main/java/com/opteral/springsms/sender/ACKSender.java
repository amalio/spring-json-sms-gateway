package com.opteral.springsms.sender;

import com.opteral.springsms.HttpGetSender;
import com.opteral.springsms.Utilities;
import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Profile("!test")
@Component
public class ACKSender {

    @Autowired
    SmsDao smsDao;

    @Autowired
    HttpGetSender httpGetSender;

    public void sendACK(ACK ack) throws GatewayException {

        SMS sms = smsDao.getSMSfromIdSMSC(ack.getIdSMSC());

        if (sms.getAckurl().isEmpty())
            return;

        String url = null;
        try {
            url = sms.getAckurl()+"?msisdn="+sms.getMsisdn()+"&status="+sms.getSms_status().getValue()+"&subid="+sms.getSubid()+"&datetime="+ URLEncoder.encode(sms.getDatetimeLastModified().toString(), "UTF-8");
        } catch (Exception ignored) {

        }

        httpGetSender.sendGet(url);

    }
}
