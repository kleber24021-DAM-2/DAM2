package model.item;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class Review {
	private String idReview;
	private int rating;
	private String date;
	private String description;
	private String title;
	private String nameCustomer;
}