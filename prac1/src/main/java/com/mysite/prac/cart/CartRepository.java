package com.mysite.prac.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.prac.cartItem.CartItem;
import com.mysite.prac.item.Item;
import com.mysite.prac.user.SiteUser;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	Cart findAllByUser(SiteUser user);

	 List<Item> findItemListByUser(SiteUser user);
	 List<Item> findItemListById(int id);
	List<CartItem> findCartItemListByUser(SiteUser user);
	

}
