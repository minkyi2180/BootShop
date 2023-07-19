package com.mysite.prac.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;



import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Setter
@Getter
@ToString
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;
	private String password;

	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private UserRole role; // 권한

	@Builder
	public SiteUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		
		
	}
	
//	public SiteUser update(String username) {
//	    this.username = username;
//	    return this;
//	}

	
	
//	private String provider;
//	private String providerId;
	

	

}
