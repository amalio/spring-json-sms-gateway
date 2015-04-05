package com.opteral.springsms;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;

public interface ProcessService {
    ResponseJSON process(RequestJSON requestJSON) throws GatewayException;
}
