package com.opteral.springsms.web;



import com.opteral.springsms.ProcessService;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/gateway")
public class GatewayController {

	@Autowired
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

}