package com.mysite.prac.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mysite.prac.orderItem.OrderItem;
import com.mysite.prac.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	//주문자의 이름
	@OneToOne
	private SiteUser user;
	
	//배송지
		private String address;
		private String postcode;
		private String address2;
		private String shipping_name; //배송받을사람의 이름
		
	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
	private List<OrderItem> orderItemList;
}
