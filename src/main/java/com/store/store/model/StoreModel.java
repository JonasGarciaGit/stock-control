package com.store.store.model;

import java.io.Serializable;

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
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_STORE")
public class StoreModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8154881860874865963L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STORE_ID")
	private Integer id;
	@Column(name = "STORE_NAME")
	private String name;
	@Column(name = "STORE_PHONE")
	private String phone;
	@Column(name = "STORE_ZIPCODE")
	private String zipcode;
	@Column(name = "STORE_ADRESS")
	private String adress;
	@Column(name = "STORE_INCOME")
	private Double income;
	
}
