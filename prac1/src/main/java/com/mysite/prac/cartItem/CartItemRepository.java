package com.mysite.prac.cartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.prac.user.SiteUser;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	CartItem findByCartIdAndItemId(int cartId, int itemId);

	List<CartItem> findByCartUser(SiteUser user);
	
	default CartItem findCartItem(int cartId, int itemId) {
		return findByCartIdAndItemId(cartId, itemId);
	}
	
	default boolean existsCartItem(int cartId, int itemId) {
		return findByCartIdAndItemId(cartId, itemId) != null;
	}
}
