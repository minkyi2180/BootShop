package com.mysite.prac.orderItem;

import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysite.prac.item.Item;
import com.mysite.prac.order.Order;

import lombok.Getter;

@Getter
@Setter
@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Item item;
	
	private int count; //주문수량
	
	private int price; //주문가격
	
	//배송
	private String postcode; //우편번호
	private String address; //주소
	private String address2; //상세주소
	private String sp_name; //배송받는 사람
	private int status; //주문 상태 
	private LocalDateTime orderDate; //주문 시간

}
