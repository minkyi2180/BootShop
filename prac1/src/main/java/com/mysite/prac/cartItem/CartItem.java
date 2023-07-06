package com.mysite.prac.cartItem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysite.prac.cart.Cart;
import com.mysite.prac.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Cart cart;
	
	//주문수량
	private int count;
}
