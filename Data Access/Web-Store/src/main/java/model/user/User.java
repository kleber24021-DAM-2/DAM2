package model.user;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class User {
	public static final String COLLECTION_NAME = "Users";
	private String password;
	private String userType;
	private String id;
	private String username;
}