package com.mysite.prac.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.prac.user.SiteUser;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	Cart findAllByUser(SiteUser user);
	

}
