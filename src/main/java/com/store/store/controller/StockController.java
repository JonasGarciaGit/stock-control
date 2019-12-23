package com.store.store.controller;

import org.apache.tomcat.jni.Time;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.service.StockService;
import com.store.store.utils.Util;
import com.store.store.vo.Stock;

@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	StockService stockService;

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> addInStock(@RequestBody Stock stock) throws JSONException {
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try {
			responseJson.getClass().newInstance();
			responseJson = stockService.addInStock(stock);
		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}

	@PostMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> removeFromStock(@RequestBody Stock stock) throws JSONException {
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try {
			responseJson.getClass().newInstance();
			responseJson = stockService.removeFromStock(stock);
		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}

}
