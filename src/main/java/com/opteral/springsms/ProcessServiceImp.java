package com.opteral.springsms;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import com.opteral.springsms.validation.CheckerSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component(value= WebApplicationContext.SCOPE_REQUEST)
public class ProcessServiceImp implements ProcessService {

    @Autowired
    private CheckerSMS checkerSMS;

    public ProcessServiceImp()
    {

    }

    public ProcessServiceImp(CheckerSMS checkerSMS)
    {
       this.checkerSMS = checkerSMS;
    }

    @Override
    public ResponseJSON process(RequestJSON requestJSON) throws GatewayException {

        check(requestJSON);

        return new ResponseJSON(ResponseJSON.ResponseCode.OK, "ok");
    }

    private void check(RequestJSON requestJSON) throws GatewayException {

        checkerSMS.check(requestJSON.getSms_request());

    }

}
