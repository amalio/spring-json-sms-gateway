package com.opteral.springsms;

import com.opteral.springsms.exceptions.LoginException;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.stereotype.Component;

@Component
public class ProcessServiceImp implements ProcessService {

    @Override
    public ResponseJSON process() throws LoginException {
        return new ResponseJSON(ResponseJSON.ResponseCode.OK, "ok");
    }

}
