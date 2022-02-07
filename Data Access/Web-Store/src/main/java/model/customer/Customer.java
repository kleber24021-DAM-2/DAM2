package model.customer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public @Data class Customer{
	public static final String COLLECTION_NAME = "Customers";
	private String telephone;
	private String address;
	private List<Purchase> purchases;
	private String name;
	private String id;
}