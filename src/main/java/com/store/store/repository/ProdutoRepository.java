package com.store.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.store.model.ProdutoModel;








@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {

	@Query("from ProdutoModel where store = :store_id")
	public List<ProdutoModel> findProductsByStore(@Param(value = "store_id") String storeId);

	@Transactional
	@Modifying
	@Query(value = "update TB_PRODUCT set PRODUCT_STOCK = :value where PRODUCT_STORE = :storeId and id =:productId" , nativeQuery = true)
	public void updateQuantityInStock(@Param(value = "value") Integer value, @Param(value = "storeId") String store,
			@Param(value = "productId") Integer productId);

	@Query(value = "select PRODUCT_STOCK from TB_PRODUCT where id = :productId and PRODUCT_STORE = :storeId" , nativeQuery = true)
	public Integer getQuantityInStock(@Param(value = "productId") Integer productId,
			@Param(value = "storeId") Integer storeId);
	
	@Query("from ProdutoModel where name = :productName")
	public ProdutoModel verifyProductExistence(@Param(value ="productName")String productName);
	
}
