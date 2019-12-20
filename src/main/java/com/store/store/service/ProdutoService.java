package com.store.store.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.store.model.ProdutoModel;
import com.store.store.model.StoreModel;
import com.store.store.repository.ProdutoRepository;
import com.store.store.repository.StoreRepository;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository productRepository;
	@Autowired
	StoreRepository storeRepository;

	public JSONObject chargeProducts() throws JSONException {
		JSONObject responseJson = new JSONObject();
		List<ProdutoModel> productList = new ArrayList<>();
		try {
			productList = readExcel();

			for (ProdutoModel p : productList) {
				productRepository.save(p);
			}

			responseJson.put("code", "0").put("description", "products added");
		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public JSONObject viewProducts(String store) throws JSONException {
		JSONObject responseJson = new JSONObject();
		List<ProdutoModel> products = new ArrayList<>();
		JSONArray productsJson = new JSONArray();
		try {
			if (store == null) {
				responseJson.put("code", "422").put("description", "store couldn't be null");
				return responseJson;
			}
			StoreModel resultStore = storeRepository.findIdByName(store);
			if (resultStore == null) {
				responseJson.put("code", "422").put("description", "store doesn't exist");
				return responseJson;
			}
			products = productRepository.findProductsByStore(resultStore.getId().toString());

			for (ProdutoModel p : products) {
				JSONObject auxJson = new JSONObject();
				auxJson.put("name", p.getName())
				.put("price", p.getPrice())
				.put("category", p.getCategory())
				.put("product_id", p.getId());
				productsJson.put(auxJson);
			}
			responseJson.put("products", productsJson);
			responseJson.put("code", "0");
			responseJson.put("description", "finded successfully");

		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public JSONObject deleteProduct(Integer id) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			productRepository.deleteById(id);
			responseJson.put("code", "0").put("description", "deleted with success");
		} catch (Exception e) {
			System.out.println("ERROR.: " + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public JSONObject updateProduct(ProdutoModel input, Integer id) throws JSONException {
		JSONObject responseJson = new JSONObject();
		try {
			if (productRepository.findById(id).isPresent() == false) {
				responseJson.put("code", "422").put("description", "this product does not exist");
				return responseJson;
			} else {
				Optional<ProdutoModel> auxInput = productRepository.findById(id);
				if (input.getName() == null) {
					input.setName(auxInput.get().getName());
				}
				if (input.getPrice() == null) {
					input.setPrice(auxInput.get().getPrice());
				}
				if (input.getCategory() == null) {
					input.setCategory(auxInput.get().getCategory());
				}
				if (input.getStore() == null) {
					input.setStore(auxInput.get().getStore());
				}
			}

			input.setId(id);
			productRepository.save(input);
			responseJson.put("code", "0").put("description", "product update");
		} catch (Exception e) {
			System.out.println("ERROR.:" + e.getMessage());
			responseJson.put("code", "1000").put("description", "INTERNAL SERVER ERROR (TIME OUT)");
		}
		return responseJson;
	}

	public List<ProdutoModel> readExcel() throws FileNotFoundException {
		List<ProdutoModel> productList = new ArrayList<ProdutoModel>();
		File file = new File("c:\\temp\\Produtos.xls");
		FileInputStream fileInputStream = new FileInputStream(file);
		HashMap<String, String> titleColumnsRead = new HashMap<String, String>();

		try {
			// Usei o enconding Cp1252 para corrigir meus problemas de encoding
			WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("Cp1252");

			// Para não mostrar avisos no console
			ws.setSuppressWarnings(true);
			Workbook workbook = Workbook.getWorkbook(fileInputStream, ws);

			// Índíce do arquivo se acaso tiver mais de um
			Sheet sheet = workbook.getSheet(0);
			// Títulos do arquivo
			Cell[] titles = sheet.getRow(0);

			titleColumnsRead.put("name", "");
			titleColumnsRead.put("price", "");
			titleColumnsRead.put("category", "");
			titleColumnsRead.put("store", "");
			titleColumnsRead.put("quantity in stock", "");

			for (int i = 1; i < sheet.getRows(); i++) {
				ProdutoModel product = new ProdutoModel();

				// Colunas
				for (int j = 0; j < sheet.getColumns(); j++) {
					// Conteúdo da celula que está sendo lida
					Cell celulaJ = sheet.getCell(j, i);
					// Verifica se a coluna que está sendo lida é alguma coluna
					// do hashmap
					if (titleColumnsRead.containsKey(titles[j].getContents())) {
						// Verifica qual é a coluna e atribui no objeto
						switch (titles[j].getContents()) {
						case "name":
							product.setName(celulaJ.getContents());
							break;
						case "price":
							product.setPrice(celulaJ.getContents());
							break;
						case "category":
							product.setCategory(celulaJ.getContents());
							break;
						case "store":
							product.setStore(celulaJ.getContents());
							break;
						case "quantity in stock":
							product.setQuantityInStock(Integer.parseInt(celulaJ.getContents()));
							break;
						}
					}
				}
				productList.add(product);
			}

		} catch (Exception e) {
			System.out.println("FILE EXCEPTION.: " + e.getMessage());
		}

		return productList;
	}
}
