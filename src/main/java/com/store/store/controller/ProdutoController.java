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

import com.store.store.model.ProdutoModel;
import com.store.store.service.ProdutoService;
import com.store.store.utils.Util;

@RestController
@RequestMapping("/product")
public class ProdutoController {

	@Autowired
	ProdutoService service;
	
	@PostMapping(value = "/charge" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> uploadProducts() throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson.getClass().newInstance();
			responseJson = service.chargeProducts();
		}catch(Exception e){
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}
	
	@GetMapping(value = "/view/{store}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> viewProducts(@PathVariable String store) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson.getClass().newInstance(); 
			responseJson = service.viewProducts(store);
		}catch(Exception e){
			System.out.println("ERROR.:" + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		String returnCode = responseJson.isNull("code") ? "1000" : responseJson.getString("code");
		Long finalRequest = System.currentTimeMillis();
		responseJson.put("timeRequest", Util.formatExecutionTimeApi(initialRequest, finalRequest));
		return new ResponseEntity<String>(responseJson.toString(), Util.validCode(returnCode));
	}
	
	@DeleteMapping(value = "/deleteProduct/{id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson.getClass().newInstance();
			responseJson = service.deleteProduct(id);
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
	public @ResponseBody ResponseEntity<String> updateProduct(@RequestBody ProdutoModel input, @PathVariable Integer id) throws JSONException{
		Long initialRequest = System.currentTimeMillis();
		JSONObject responseJson = new JSONObject();
		try{
			responseJson = JSONObject.class.newInstance();
			responseJson = service.updateProduct(input, id);
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
