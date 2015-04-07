package com.opteral.springsms;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import com.opteral.springsms.json.SMS_Response;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.validation.CheckerSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component(value= WebApplicationContext.SCOPE_REQUEST)
public class ProcesorImp implements Procesor {

    @Autowired
    private CheckerSMS checkerSMS;

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private SpringAuthentication authentication;

    private boolean forDelete;

    public ProcesorImp()
    {

    }

    public ProcesorImp(CheckerSMS checkerSMS, SmsDao smsDao, SpringAuthentication authentication)
    {
        this.checkerSMS = checkerSMS;
        this.smsDao = smsDao;
        this.authentication = authentication;
    }

    @Override
    public ResponseJSON post(RequestJSON requestJSON) throws GatewayException {

        check(requestJSON);

        return new ResponseJSON(processList(requestJSON));
    }

    @Override
    public ResponseJSON delete(RequestJSON requestJSON) throws GatewayException {
        forDelete = true;
        return new ResponseJSON(processList(requestJSON));
    }

    private void check(RequestJSON requestJSON) throws GatewayException {

        checkerSMS.check(requestJSON.getSms_request());

    }

    private  List<SMS_Response> processList(RequestJSON requestJSON) throws GatewayException {

        List<SMS_Response> sms_responses = new ArrayList<SMS_Response>() ;


        for (JSON_SMS jsonSMS : requestJSON.getSms_request())
        {
            try
            {
                SMS sms = new SMS(jsonSMS, authentication.getUser().getId());

                persist(sms);

                sms_responses.add(new SMS_Response(sms, SMS_Response.OK));

            }
            catch (Exception e) {
                sms_responses.add(new SMS_Response(jsonSMS, SMS_Response.ERROR));
            }
        }

        return sms_responses;
    }

    private void persist(SMS sms) throws GatewayException {
        if (!sms.isTest())
        {
            if (forDelete)
                smsDao.delete(sms);
            else if (sms.getId() > 0)
                smsDao.insert(sms);
            else
                smsDao.update(sms);
        }
    }

}
