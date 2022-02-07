package model.customer;

import lombok.Builder;
import lombok.Data;
import model.item.Review;

@Builder
public @Data class Purchase {
	private String date;
	private Review review;
	private String idItem;
	private String idPurchase;
}