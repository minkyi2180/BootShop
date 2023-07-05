package com.mysite.prac.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateForm {
	@NotEmpty(message="비밀번호는 필수항목입니다")
	private String passowrd;
	
	@NotEmpty(message="이메일은 필수항목입니다")
	private String email;
}
