package model.item;

import lombok.Builder;
import lombok.Data;
import model.customer.Purchase;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
public @Data class Item {
	public static final String COLLECTION_NAME = "Items";
	private List<Review> reviews;
	private List<Purchase> purchases;
	private double price;
	private String name;
	private String company;
	private ObjectId id;
}