package com.store.store.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "PRODUCT_NAME")
	private String name;
	@Column(name = "PRODUCT_PRICE")
	private String price;
	@Column(name = "PRODUCT_CATEGORY")
	private String category;
	@Column(name = "PRODUCT_STORE")
	private String store;
	@Column(name = "PRODUCT_STOCK")
	private Integer quantityInStock;
	
}
