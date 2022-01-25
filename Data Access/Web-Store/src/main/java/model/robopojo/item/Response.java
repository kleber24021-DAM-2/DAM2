package model.robopojo.item;

import java.util.List;
import lombok.Data;

public @Data class Response{
	private List<ReviewsItem> reviews;
	private List<PurchasesItem> purchases;
	private double price;
	private int idItem;
	private String name;
	private String company;
	private Id id;
}