package com.opteral.springsms.web;


import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@Controller
@RequestMapping(value = "/gateway")
@RestController
public class GatewayController {
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD}, headers="Accept=application/json")
	public ResponseJSON gateway() {

		ResponseJSON responseJSON = new ResponseJSON(new GatewayException("error message"));

		return responseJSON;

	}
}