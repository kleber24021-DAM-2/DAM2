package model.robopojo.customer;

import lombok.Data;

public @Data class Reviews{
	private String date;
	private int rating;
	private String description;
	private int idReview;
	private String title;
}