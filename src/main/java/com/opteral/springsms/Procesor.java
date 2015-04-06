package com.opteral.springsms;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;

public interface Procesor {
    ResponseJSON post(RequestJSON requestJSON) throws GatewayException;
    ResponseJSON delete(RequestJSON requestJSON) throws GatewayException;
}
