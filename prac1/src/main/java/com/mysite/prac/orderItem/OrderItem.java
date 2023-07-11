package com.mysite.prac.orderItem;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysite.prac.item.Item;
import com.mysite.prac.order.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem {
	
	//주문번호
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Item item;
	
	
	//주문수량
	private int count;
	
	//주문가격
	private int price;
	
	//배송지
	private String address;
	private String postcode;
	private String address2;
	private String shipping_name; //배송받을사람의 이름
	
	//입금 전(1), 입금 확인(2) 기본값 =1
	private int depositStatus;
	
	//주문날짜
	private LocalDateTime orderDate;
	
}
