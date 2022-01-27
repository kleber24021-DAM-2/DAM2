package model.customer;

import lombok.Data;

import java.util.List;

public @Data class Customer{
	public static final String COLLECTION_NAME = "Customers";
	private String telephone;
	private Address address;
	private List<Purchase> purchases;
	private String name;
	private String id;
}