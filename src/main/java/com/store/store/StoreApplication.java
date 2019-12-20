package com.store.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.store.store.model.StoreModel;
import com.store.store.repository.StoreRepository;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Autowired
	StoreRepository repository; 
	
	@Override
	public void run(String... args) throws Exception {
		StoreModel st1 = new StoreModel(1 ,"Magu Lu", "11952692620", "03591070", "Rua das macas" ,6300.00);
		StoreModel st2 = new StoreModel(2 ,"Casas Bahia", "11269265784", "03672541", "Rua das peras" ,5700.00);
		StoreModel st3 = new StoreModel(3 ,"Leroy Merlyn", "11784562897", "03562145", "Rua dos abacates" ,12800.00);
		StoreModel st4 = new StoreModel(4 ,"Submarino", "11784529867", "03628469", "Rua das uvas" ,1200.00);
		repository.save(st1);
		repository.save(st2);
		repository.save(st3);
		repository.save(st4);
				
	}

	
	
}
