package com.mysite.prac.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@OneToOne
	private SiteUser user;
	
	   //주소
//  private String postcode; //우편번호
//  private String address; //주소
//  private String address2; //상세주소
  
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
	private List<OrderItem> orderItemList;

}
