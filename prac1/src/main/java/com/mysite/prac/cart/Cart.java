package com.mysite.prac.cart;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.mysite.prac.cartItem.CartItem;
import com.mysite.prac.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@OneToOne
	private SiteUser user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
	private List<CartItem> cartItemList;
}
