package model.robopojo.user;

import lombok.Data;

public @Data class User {
	private String password;
	private String userType;
	private String id;
	private String dni;
	private String username;
}