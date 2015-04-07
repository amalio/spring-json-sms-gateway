package com.opteral.springsms.web;



import com.opteral.springsms.Processor;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/gateway")
public class GatewayController {

	@Autowired
	private Processor processor;

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@RequestMapping(method = {RequestMethod.POST}, headers="Accept=application/json")
	@ResponseBody
	public ResponseJSON gatewayPost(@RequestBody RequestJSON requestJSON) throws GatewayException {

		return processor.post(requestJSON);

	}

	@RequestMapping(method = {RequestMethod.DELETE}, headers="Accept=application/json")
	@ResponseBody
	public ResponseJSON gatewayDelete(@RequestBody RequestJSON requestJSON) throws GatewayException {

		return processor.delete(requestJSON);

	}

}