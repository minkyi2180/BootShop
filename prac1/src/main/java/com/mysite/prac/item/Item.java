package com.mysite.prac.item;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysite.prac.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
private String name; //상품이름
	
	@Column(columnDefinition = "TEXT")
	private String text; //상품내용
	
	
	private int price; //가격
	private int stock; //재고
	
	private int isSoldOut; //판매여부 (0 = 판매 / 1 = 판매중)
	
	@Column(length = 150)
	private String filename;
	
	@Column(length = 300)
	private String filepath;
	
	private LocalDateTime createDate; //상품등록일
	
	private int hit; //조회수
	
	private int sell_count; //판매량
	
	@ManyToOne
	private Category category;

}
