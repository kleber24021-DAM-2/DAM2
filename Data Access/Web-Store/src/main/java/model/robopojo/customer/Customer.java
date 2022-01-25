package model.robopojo.customer;

import lombok.Data;

import java.util.List;

public @Data class Customer{
	private String telephone;
	private Address address;
	private List<PurchasesItem> purchases;
	private String name;
	private String id;
}