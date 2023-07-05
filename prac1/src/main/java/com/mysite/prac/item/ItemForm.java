package com.mysite.prac.item;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
	@NotEmpty(message="상품명을 입력해주세요")
	private String name;
	private String text;
	private int price;
	private int stock;
	private MultipartFile file;

}
