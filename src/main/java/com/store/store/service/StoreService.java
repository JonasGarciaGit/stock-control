package com.store.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.store.model.StoreModel;
import com.store.store.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	StoreRepository repository;
	
	public JSONObject createStore(StoreModel input) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			if (input.getName().isEmpty() || input.getPhone().isEmpty() || input.getZipcode().isEmpty()
					|| input.getAdress().isEmpty()) {
				responseJson.put("code", "422").put("description", "no field can be empty (except income)");
				return responseJson;
			}
			if (input.getIncome() == null) {
				input.setIncome(0.0);
			}
			repository.save(input);
			responseJson.put("code", "0").put("description", "Store created");
		} catch (Exception e) {
			System.out.println("ERROR.:" + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public JSONObject findStores() throws JSONException {
		JSONObject responseJson = new JSONObject();
		JSONArray responseList = new JSONArray();
		List<StoreModel> resultQueryList = new ArrayList<>();
		try {
			resultQueryList = repository.findAll();
			for (StoreModel store : resultQueryList) {
				JSONObject tempJson = new JSONObject();
				tempJson.put("id", store.getId());
				tempJson.put("name", store.getName());
				tempJson.put("phone", store.getPhone());
				tempJson.put("zipcode", store.getZipcode());
				tempJson.put("adress", store.getAdress());
				tempJson.put("income", store.getIncome());
				responseList.put(tempJson);
			}
			responseJson.put("stores", responseList);
			responseJson.put("code", "0").put("description", "successful search");
		} catch (Exception e) {
			System.out.println("ERROR.:" + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}
	
	public JSONObject deleteStore(Integer id) throws JSONException{
		JSONObject responseJson = new JSONObject();
		try{
			repository.deleteById(id);
			responseJson.put("code", "0").put("description", "deleted with success");
		}catch(Exception e){
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}
	
	public JSONObject updateStore(StoreModel input, Integer id) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			if(repository.findById(id).isPresent() == false){
				responseJson.put("code", "422").put("description", "this store does not exist");
				return responseJson;
			}else{
				Optional<StoreModel> auxInput = repository.findById(id);
				if(input.getIncome() == null){
					input.setIncome(auxInput.get().getIncome());
				}
				if(input.getAdress() == null){
					input.setAdress(auxInput.get().getAdress());
				}
				if(input.getName() == null){
					input.setName(auxInput.get().getName());
				}
				if(input.getPhone() == null){
					input.setPhone(auxInput.get().getPhone());
				}
				if(input.getZipcode() == null){
					input.setZipcode(auxInput.get().getZipcode());
				}
			}
			
			input.setId(id);
			repository.save(input);
			responseJson.put("code", "0").put("description", "Store update");
		} catch (Exception e) {
			System.out.println("ERROR.:" + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}
}
