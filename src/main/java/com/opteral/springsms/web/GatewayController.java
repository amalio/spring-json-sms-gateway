package com.opteral.springsms.web;



import com.opteral.springsms.ProcessService;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.exceptions.LoginException;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/gateway")
public class GatewayController {

	private ProcessService processService;

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD}, headers="Accept=application/json")
	@ResponseBody
	public ResponseJSON gateway() throws GatewayException {

		return processService.process();

	}


	@ExceptionHandler(LoginException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseJSON handle(LoginException loginException)
	{
		return new ResponseJSON(loginException);
	}

	@ExceptionHandler(GatewayException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseJSON handle(GatewayException gatewayException)
	{
		return new ResponseJSON(gatewayException);
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseJSON handle(Exception e)
	{
		return new ResponseJSON(ResponseJSON.ResponseCode.ERROR_GENERAL, e.toString());
	}




}