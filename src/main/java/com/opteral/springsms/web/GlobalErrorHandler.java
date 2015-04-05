package com.opteral.springsms.web;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.Parser;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalErrorHandler {



    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handle(AuthenticationException authenticationException)
    {
        return Parser.getJSON(new ResponseJSON(authenticationException));
    }

    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(GatewayException gatewayException)
    {
        return Parser.getJSON(new ResponseJSON(gatewayException));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(Exception e)
    {
        return Parser.getJSON(new ResponseJSON(ResponseJSON.ResponseCode.ERROR_GENERAL, e.toString()));
    }

}
