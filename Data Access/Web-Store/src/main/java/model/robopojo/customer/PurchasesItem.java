package model.robopojo.customer;

import lombok.Data;

public @Data class PurchasesItem{
	private String date;
	private Reviews reviews;
	private int idItem;
	private int idPurchase;
}