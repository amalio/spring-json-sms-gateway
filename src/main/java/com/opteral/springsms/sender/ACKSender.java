package com.opteral.springsms.sender;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.http.HttpHelper;
import com.opteral.springsms.json.JSON_ACK;
import com.opteral.springsms.json.Parser;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("!test")
@Component
public class ACKSender {

    @Autowired
    SmsDao smsDao;

    public String sendACK(ACK ack) throws GatewayException {

        SMS sms = smsDao.getSMSfromIdSMSC(ack.getIdSMSC());

        if (sms.getAckurl().isEmpty())
            return "";

        JSON_ACK json_ack = new JSON_ACK(sms);

        HttpHelper httpHelper = new HttpHelper(sms.getAckurl(), json_ack);

        try {
            httpHelper.postRequest();
            return Parser.getJSON(json_ack);
        } catch (IOException ignored) {
            return "";
        }

    }
}
