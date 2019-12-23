package com.store.store.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.model.StoreModel;
import com.store.store.service.StoreService;
import com.store.store.utils.Util;

@RestController
@RequestMapping(value = "/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@PostMapping(value = "/create" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> createStore(@RequestBody StoreModel input) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson = JSONObject.class.newInstance();
			responseJson = storeService.createStore(input);
		}catch(Exception e){
		responseJson.put("code","1000").put("description","INTERNAL SERVER ERROR (TIME OUT)");
		System.out.println("ERROR.: " + e.getMessage());
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(),Util.validCode(returnCode));
	}
	
	@GetMapping(value = "/findStore" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> findStores() throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson = JSONObject.class.newInstance();
			responseJson = storeService.findStores();
		}catch(Exception e){
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
			System.out.println("ERROR.:" + e.getMessage());
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}
	
	@DeleteMapping(value = "/deleteStore/{id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> deleteStore(@PathVariable Integer id) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson.getClass().newInstance();
			responseJson = storeService.deleteStore(id);
		}catch(Exception e){
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description","INTERNAL SERVER ERROR (TIME OUT)");
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}
	
	@PutMapping(value = "/update/{id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> updateStore(@RequestBody StoreModel input, @PathVariable Integer id) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson = JSONObject.class.newInstance();
			responseJson = storeService.updateStore(input, id);
		}catch(Exception e){
		responseJson.put("code","1000").put("description","INTERNAL SERVER ERROR (TIME OUT)");
		System.out.println("ERROR.: " + e.getMessage());
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(),Util.validCode(returnCode));
	}

}
