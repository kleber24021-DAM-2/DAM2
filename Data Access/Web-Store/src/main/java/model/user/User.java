package model.user;

import lombok.Data;

public @Data class User {
	public static final String COLLECTION_NAME = "Users";
	private String password;
	private String userType;
	private String id;
	private String dni;
	private String username;
}