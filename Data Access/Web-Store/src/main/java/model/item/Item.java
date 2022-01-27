package model.item;

import lombok.Data;
import model.customer.Purchase;

import java.util.List;

public @Data class Item {
	public static final String COLLECTION_NAME = "Items";
	private List<Review> reviews;
	private List<Purchase> purchases;
	private double price;
	private int idItem;
	private String name;
	private String company;
	private Id id;
}