package com.opteral.springsms;

import com.opteral.springsms.database.SMSDAO;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import com.opteral.springsms.json.SMS_Response;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.validation.CheckerSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Component(value= WebApplicationContext.SCOPE_REQUEST)
public class ProcessServiceImp implements ProcessService {

    @Autowired
    private CheckerSMS checkerSMS;

    @Autowired
    private SMSDAO smsdao;

    @Autowired
    private SpringAuthentication authentication;

    public ProcessServiceImp()
    {

    }

    public ProcessServiceImp(CheckerSMS checkerSMS, SMSDAO smsdao, SpringAuthentication authentication)
    {
        this.checkerSMS = checkerSMS;
        this.smsdao = smsdao;
        this.authentication = authentication;
    }

    @Override
    public ResponseJSON process(RequestJSON requestJSON) throws GatewayException {

        check(requestJSON);

        return new ResponseJSON(listProcess(requestJSON));
    }

    private void check(RequestJSON requestJSON) throws GatewayException {

        checkerSMS.check(requestJSON.getSms_request());

    }

    private  List<SMS_Response> listProcess(RequestJSON requestJSON) throws GatewayException {

        List<SMS_Response> sms_responses = new ArrayList<SMS_Response>() ;


        for (JSON_SMS jsonSMS : requestJSON.getSms_request())
        {
            try
            {

                SMS sms = new SMS(jsonSMS, authentication.getUserId());

                if (!sms.isTest())
                    smsdao.persist(sms);

                sms_responses.add(new SMS_Response(sms, SMS_Response.OK));


            }
            catch (Exception e) {
                sms_responses.add(new SMS_Response(jsonSMS, SMS_Response.ERROR));

            }
        }

        return sms_responses;
    }

}
