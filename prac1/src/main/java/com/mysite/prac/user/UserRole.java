package com.mysite.prac.user;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	UserRole(String value){
		this.value = value;
		this.key = key;
	}
	private String value;
	private String key;

}
