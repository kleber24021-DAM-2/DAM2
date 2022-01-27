package model.item;

import lombok.Data;

public @Data class Review {
	private String date;
	private int rating;
	private String description;
	private int idReview;
	private String title;
	private String nameCustomer;
}