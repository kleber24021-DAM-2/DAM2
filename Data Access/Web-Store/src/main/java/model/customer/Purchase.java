package model.customer;

import lombok.Data;
import model.item.Review;

public @Data class Purchase {
	private String date;
	private Review review;
	private int idItem;
	private int idPurchase;
}