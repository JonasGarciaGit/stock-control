package com.store.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.store.model.StoreModel;

@Repository
public interface StoreRepository extends JpaRepository<StoreModel, Integer>{

	@Query("from StoreModel where name = :name")
	public StoreModel findIdByName(@Param(value = "name")String name); 
}
