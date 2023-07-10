package com.mysite.prac.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	
	   //주소
//    private String postcode; //우편번호
//    private String address; //주소
//    private String address2; //상세주소
//    
//    @Column(unique = true)
//    private String phone_number; //폰번호

	@Enumerated(EnumType.STRING)
	private UserRole role; // 권한

}
