package com.store.store.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.store.repository.ProdutoRepository;
import com.store.store.repository.StoreRepository;
import com.store.store.vo.Stock;

@Service
public class StockService {

	@Autowired
	StoreRepository storeRepository;
	@Autowired
	ProdutoRepository produtoRepository;

	public JSONObject addInStock(Stock stock) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			if (stock.getValue() == null) {
				responseJson.put("code", "422").put("description", "the value can't be null.");
				return responseJson;
			}
			if (stock.getValue() <= 0) {
				responseJson.put("code", "422").put("description", "the value should be more than zero.");
			}
			if (storeRepository.findById(Integer.parseInt(stock.getStoreId())) == null) {
				responseJson.put("code", "422").put("description", "store doesn't exist.");
				return responseJson;
			}

			Integer stockQuantity = produtoRepository.getQuantityInStock(stock.getProductId(),
					Integer.parseInt(stock.getStoreId()));
			produtoRepository.updateQuantityInStock(stockQuantity + stock.getValue(), stock.getStoreId(),
					stock.getProductId());
			responseJson.put("code", "0").put("description", "quantity in stock updated with sucessfuly.");

		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public JSONObject removeFromStock(Stock stock) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			if (stock.getValue() == null) {
				responseJson.put("code", "422").put("description", "the value can't be null.");
				return responseJson;
			}
			if (stock.getValue() <= 0) {
				responseJson.put("code", "422").put("description", "the value should be more than zero.");
			}
			if (storeRepository.findById(Integer.parseInt(stock.getStoreId())) == null) {
				responseJson.put("code", "422").put("description", "store doesn't exist.");
				return responseJson;
			}

			Integer stockQuantity = produtoRepository.getQuantityInStock(stock.getProductId(),
					Integer.parseInt(stock.getStoreId()));

			if (stock.getValue() > stockQuantity) {
				responseJson.put("code", "422").put("description",
						"the value can't more than the current quantity in stock.");
				return responseJson;
			}

			produtoRepository.updateQuantityInStock(stockQuantity - stock.getValue(), stock.getStoreId(),
					stock.getProductId());
			responseJson.put("code", "0").put("description", "quantity in stock updated with sucessfuly.");

		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}
}
